package com.buspoint.usuario.aluno;

import com.buspoint.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "aluno")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Aluno extends Usuario {

    @NotNull
    @Column(name = "numero_matricula", nullable = false, unique = true)
    private Integer numeroMatricula;

    @NotBlank
    @Size(max = 50)
    @Column(name = "periodo", nullable = false, length = 50)
    private String periodo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "status_matricula", nullable = false)
    private Boolean statusMatricula;
}

