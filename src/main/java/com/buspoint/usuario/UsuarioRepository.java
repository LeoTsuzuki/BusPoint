package com.buspoint.usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLoginUsuario(String loginUsuario);

    boolean existsByEmailUsuario(String emailUsuario);

    boolean existsByLoginUsuario(String loginUsuario);

    boolean existsByEmailUsuarioAndIdUsuarioNot(String emailUsuario, Long idUsuario);

    boolean existsByLoginUsuarioAndIdUsuarioNot(String loginUsuario, Long idUsuario);
}
