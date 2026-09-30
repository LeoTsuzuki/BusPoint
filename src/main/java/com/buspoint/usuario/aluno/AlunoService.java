package com.buspoint.usuario.aluno;

import java.nio.charset.StandardCharsets;
import java.util.List;
import com.buspoint.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AlunoService {

    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AlunoService(AlunoRepository repository,
            UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Aluno> buscarTodos() {
        return repository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

    @Transactional
    public Aluno cadastrar(Aluno dados) {
        validarDuplicidade(dados, null);
        String senha = dados.getSenhaUsuario();
        if (senha == null || senha.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a senha");
        }
        // O limite do BCrypt e medido em bytes, nao em caracteres.
        if (senha.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha excede 72 bytes");
        }
        dados.setIdUsuario(null);
        dados.setStatusUsuario(true);
        dados.setFotoUsuario(null);
        dados.setSenhaUsuario(passwordEncoder.encode(senha));
        dados.setStatusMatricula(true);
        return repository.saveAndFlush(dados);
    }

    @Transactional
    public Aluno atualizar(Long id, Aluno dados) {
        Aluno existente = buscarPorId(id);
        if (dados.getSenhaUsuario() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A edicao cadastral nao altera senhas. Omita senhaUsuario");
        }
        validarDuplicidade(dados, id);
        // Alteramos somente os campos cadastrais permitidos.
        existente.setNomeUsuario(dados.getNomeUsuario());
        existente.setEmailUsuario(dados.getEmailUsuario());
        existente.setTelefoneUsuario(dados.getTelefoneUsuario());
        existente.setLoginUsuario(dados.getLoginUsuario());
        existente.setNumeroMatricula(dados.getNumeroMatricula());
        existente.setPeriodo(dados.getPeriodo());
        return repository.saveAndFlush(existente);
    }

    @Transactional
    public void desativar(Long id) {
        Aluno existente = buscarPorId(id);
        existente.setStatusUsuario(false);
        repository.saveAndFlush(existente);
    }

    private void validarDuplicidade(Aluno dados, Long id) {
        boolean emailEmUso;
        boolean loginEmUso;
        if (id == null) {
            emailEmUso = usuarioRepository.existsByEmailUsuario(dados.getEmailUsuario());
            loginEmUso = usuarioRepository.existsByLoginUsuario(dados.getLoginUsuario());
        } else {
            // Na edicao, o proprio usuario pode manter seu email e login.
            emailEmUso = usuarioRepository.existsByEmailUsuarioAndIdUsuarioNot(dados.getEmailUsuario(), id);
            loginEmUso = usuarioRepository.existsByLoginUsuarioAndIdUsuarioNot(dados.getLoginUsuario(), id);
        }
        if (emailEmUso || loginEmUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ou login ja cadastrado");
        }
        boolean identificadorEmUso = id == null
                ? repository.existsByNumeroMatricula(dados.getNumeroMatricula())
                : repository.existsByNumeroMatriculaAndIdUsuarioNot(dados.getNumeroMatricula(), id);
        if (identificadorEmUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "NumeroMatricula ja cadastrado");
        }
    }
}
