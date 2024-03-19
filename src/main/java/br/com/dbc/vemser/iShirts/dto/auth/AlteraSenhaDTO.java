package br.com.dbc.vemser.iShirts.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlteraSenhaDTO {
    @NotNull
    private String email;
    @NotNull
    private String senhaAtual;

    @NotNull
    private String senhaNova;
}
