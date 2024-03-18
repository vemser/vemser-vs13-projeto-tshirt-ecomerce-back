package br.com.dbc.vemser.iShirts.dto.endereco;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Validated
@Data
public class EnderecoCreateDTO {

    @NotNull(message = "id pessoa nulo, por favor preencher corretamente corretamente")
    private Integer idPessoa;

    @NotNull(message = "por favor preencha o logradouro corretamente")
    private String logradouro;

    @NotNull(message = "por favor preencha o numero corretamente")
    private Integer numero;

    private String complemento;

    private String referencia;

    @NotNull(message = "por favor preencha o bairro corretamente")
    private String bairro;

    @NotNull(message = "por favor preencha o cep corretamente")
    private String cep;

    @NotNull(message = "por favor preencha o estado corretamente")
    private String estado;

    @NotNull(message = "por favor preencha o pais corretamente")
    private String pais;

    private Date criadoEm;

    private Date editadoEm;
}
