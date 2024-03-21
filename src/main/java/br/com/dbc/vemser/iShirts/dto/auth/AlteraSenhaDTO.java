package br.com.dbc.vemser.iShirts.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlteraSenhaDTO {
    @NotNull
    @Schema(description = "E-mail do Usuário", required = true, example = "cliente@dbccompany.com.br")
    private String email;
    @NotNull
    @Schema(description = "Senha do Usuário", required = true, example = "Senha123@")
    private String senhaAtual;

    @NotNull
    @Schema(description = "Senha do Usuário", required = true, example = "Senha321@")
    private String senhaNova;
}
