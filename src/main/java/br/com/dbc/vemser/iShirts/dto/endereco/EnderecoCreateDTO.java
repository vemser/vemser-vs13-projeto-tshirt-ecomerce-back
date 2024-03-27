package br.com.dbc.vemser.iShirts.dto.endereco;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoCreateDTO {

    @Schema(description = "id da pessoa dona do endereço", example = "1", required = true)
    @NotNull(message = "id pessoa nulo, por favor preencher corretamente corretamente")
    private Integer idPessoa;

    @Schema(description = "Logradouro do endereço", example = "rua cafelandia", required = true)
    @NotNull(message = "por favor preencha o logradouro corretamente")
    private String logradouro;

    @Schema(description = "numero do endereço da pessoa", example = "125", required = true)
    @NotNull(message = "por favor preencha o numero corretamente")
    private Integer numero;

    @Schema(description = "complemente da casa da pessoa", example = "1")
    private String complemento;

    @Schema(description = "uma referencia proxima a casa da pessoa", example = "ao lado de uma mecanica")
    private String referencia;

    @Schema(description = "O bairro do endereço da pessoa", example = "engenho novo", required = true)
    @NotNull(message = "por favor preencha o bairro corretamente")
    private String bairro;

    @Schema(description = "cep do endereço ", example = "0616158", required = true)
    @NotNull(message = "por favor preencha o cep corretamente")
    private String cep;

    @Schema(description = "cidade do endereço ", example = "Barueri", required = true)
    @NotNull(message = "por favor preencha a cidade corretamente")
    private String cidade;

    @Schema(description = "estado do endereço da pessoa", example = "SP", required = true)
    @NotNull(message = "por favor preencha o estado corretamente")
    private String estado;

    @Schema(description = "país do endereço da pessoa", example = "BR", required = true)
    @NotNull(message = "por favor preencha o pais corretamente")
    private String pais;

    @Hidden
    private Timestamp criadoEm;

    @Hidden
    private Timestamp editadoEm;
}
