package br.com.dbc.vemser.iShirts.dto.endereco;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.util.Date;


@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EnderecoDTO {
    private Integer idEndereco;


    private Integer idPessoa;

    private String logradouro;

    private Integer numero;

    private String complemento;

    private String referencia;

    private String cidade;

    private String bairro;

    private String cep;


    private String estado;


    private String pais;


    private Timestamp criadoEm;


    private Timestamp editadoEm;

}
