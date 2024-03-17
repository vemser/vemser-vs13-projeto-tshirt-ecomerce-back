package br.com.dbc.vemser.iShirts.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

public class UsuarioCreateDTO {

    @NotBlank
    @Schema(description = "E-mail do Usuário", required = true, example = "cliente@dbccompany.com.br")
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "A senha deve conter pelo menos 1 caractere especial")
    private String senha;
}
