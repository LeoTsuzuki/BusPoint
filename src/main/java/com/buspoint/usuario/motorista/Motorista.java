package com.buspoint.usuario.motorista;

import java.time.LocalDate;
import com.buspoint.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "motorista")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Motorista extends Usuario {

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero_cnh", nullable = false, unique = true, length = 20)
    private String numeroCnh;

    @NotBlank
    @Size(max = 10)
    @Column(name = "categoria_cnh", nullable = false, length = 10)
    private String categoriaCnh;

    @NotNull
    @Column(name = "validade_cnh", nullable = false)
    private LocalDate validadeCnh;
}

