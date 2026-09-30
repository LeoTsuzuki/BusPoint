package com.buspoint.usuario.motorista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    boolean existsByNumeroCnh(String valor);

    boolean existsByNumeroCnhAndIdUsuarioNot(String valor, Long idUsuario);
}
