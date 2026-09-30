package com.buspoint.usuario.motorista;

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
@RequestMapping("/api/motoristas")
public class MotoristaController {

    private final MotoristaService service;

    public MotoristaController(MotoristaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Motorista> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Motorista buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Motorista> cadastrar(@Valid @RequestBody Motorista dados) {
        Motorista cadastrado = service.cadastrar(dados);
        return ResponseEntity.created(URI.create("/api/motoristas/" + cadastrado.getIdUsuario()))
                .body(cadastrado);
    }

    @PutMapping("/{id}")
    public Motorista atualizar(@PathVariable Long id, @Valid @RequestBody Motorista dados) {
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
