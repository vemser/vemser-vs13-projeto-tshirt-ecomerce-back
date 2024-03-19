package br.com.dbc.vemser.iShirts.dto.pessoa;

import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class PessoaUpdateDTO {

    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome da pessoa", example = "João")
    private String nome;

    @Size(min = 3, max = 100, message = "O sobrenome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Sobrenome da pessoa", example = "Silva")
    private String sobrenome;

    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
    @Schema(description = "CPF da pessoa", example = "12345678901")
    private String cpf;

    @Size(min = 11, max = 11, message = "O celular deve ter 11 caracteres")
    @Schema(description = "Celular da pessoa", example = "12345678901")
    private String celular;

    @Schema(description = "Data de nascimento do cliente", example = "01/01/2000")
    private Date dataNascimento;

    @Schema(description = "Preferência da pessoa", example = "Camisetas de banda")
    private String preferencia;

    @Schema(description = "Status ativo da pessoa", example = "1")
    private String ativo;

}