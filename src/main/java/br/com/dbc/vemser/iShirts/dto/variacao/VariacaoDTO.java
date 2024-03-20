package br.com.dbc.vemser.iShirts.dto.variacao;

import br.com.dbc.vemser.iShirts.model.Foto;
import br.com.dbc.vemser.iShirts.model.Produto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class VariacaoDTO {

    private Integer idVariacoes;

    private Produto produto;

    private Foto foto;

    private String sku;

    private String cor;

    private String tamanho;

    private Double preco;

    private Integer taxaDesconto;

    private String ativo;

    private Timestamp criado;

    private Timestamp editado;
}
