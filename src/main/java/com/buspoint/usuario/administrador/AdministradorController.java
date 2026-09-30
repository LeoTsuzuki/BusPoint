package com.buspoint.usuario.administrador;

import java.net.URI;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Administrador> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Administrador buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Administrador> cadastrar(@Valid @RequestBody Administrador dados) {
        Administrador cadastrado = service.cadastrar(dados);
        return ResponseEntity.created(URI.create("/api/administradores/" + cadastrado.getIdUsuario()))
                .body(cadastrado);
    }

    @PutMapping("/{id}")
    public Administrador atualizar(@PathVariable Long id, @Valid @RequestBody Administrador dados) {
        return service.atualizar(id, dados);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    // Trata tambem duplicidades causadas por dois cadastros simultaneos.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarConflito(DataIntegrityViolationException erro) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("mensagem", "Os dados conflitam com um registro ou uma restricao do banco"));
    }
}
