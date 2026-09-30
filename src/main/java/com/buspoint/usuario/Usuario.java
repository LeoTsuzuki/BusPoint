package com.buspoint.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // permite que o campo apareça no JSON de resposta, mas impede que ele seja preenchido pelo JSON enviado à API.
    private Long idUsuario;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nome_usuario", nullable = false, length = 50)
    private String nomeUsuario;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email_usuario", nullable = false, length = 100, unique = true)
    private String emailUsuario;

    @NotBlank
    @Size(max = 13)
    @Column(name = "telefone_usuario", nullable = false, length = 13)
    private String telefoneUsuario;

    @NotBlank
    @Size(max = 40)
    @Column(name = "login_usuario", nullable = false, length = 40, unique = true)
    private String loginUsuario;

    // A API recebe a senha, mas nunca a inclui no JSON de resposta.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha_usuario", nullable = false, length = 150)
    private String senhaUsuario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "status_usuario", nullable = false)
    private Boolean statusUsuario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "foto_usuario")
    private String fotoUsuario;
}
