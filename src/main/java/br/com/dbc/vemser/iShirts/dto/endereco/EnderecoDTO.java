package br.com.dbc.vemser.iShirts.dto.endereco;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Validated

public class EnderecoDTO {
    private Integer idEndereco;

    private Pessoa pessoa;

    private Integer idPessoa;

    private String logradouro;

    private Integer numero;

    private String complemento;

    private String referencia;


    private String bairro;

    private String cep;


    private String estado;


    private String pais;


    private Date criadoEm;


    private Date editadoEm;

}
