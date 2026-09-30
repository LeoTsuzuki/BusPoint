package com.buspoint.usuario.aluno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByNumeroMatricula(Integer valor);

    boolean existsByNumeroMatriculaAndIdUsuarioNot(Integer valor, Long idUsuario);
}
