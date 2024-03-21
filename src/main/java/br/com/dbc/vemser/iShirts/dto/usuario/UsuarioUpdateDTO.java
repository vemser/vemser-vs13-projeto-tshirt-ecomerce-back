package br.com.dbc.vemser.iShirts.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {
    @NotBlank
    @Schema(description = "E-mail do Usuário", required = true, example = "cliente@dbccompany.com.br")
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Size(max = 60, message = "A senha deve ter no máximo 60 caracteres")
    @Schema(description = "Senha do Usuário", required = true, example = "Senha123@")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "A senha deve conter pelo menos 1 caractere especial")
    private String senha;
}
