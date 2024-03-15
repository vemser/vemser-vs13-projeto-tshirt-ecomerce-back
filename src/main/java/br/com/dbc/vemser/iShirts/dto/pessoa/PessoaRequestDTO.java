package br.com.dbc.vemser.iShirts.dto.pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class PessoaRequestDTO {

    @NotNull
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome da pessoa", example = "João", required = true)
    private String nome;

    @NotNull
    @Size(min = 3, max = 100, message = "O sobrenome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Sobrenome da pessoa", example = "Silva", required = true)
    private String sobrenome;

    @NotNull
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
    @Schema(description = "CPF da pessoa", example = "12345678901", required = true)
    private String cpf;

    @NotNull
    @Size(min = 11, max = 11, message = "O celular deve ter 11 caracteres")
    @Schema(description = "Celular da pessoa", example = "12345678901", required = true)
    private String celular;

    @NotNull
    @Schema(description = "Data de nascimento da pessoa", example = "1990-01-01", required = true)
    private Date dataNascimento;

    @NotNull
    private String preferencia;
    private char ativo;
    private Date criado;
    private Date editado;

}
