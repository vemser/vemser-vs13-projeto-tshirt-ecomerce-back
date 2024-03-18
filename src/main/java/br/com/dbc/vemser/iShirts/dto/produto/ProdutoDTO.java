package br.com.dbc.vemser.iShirts.dto.produto;

import br.com.dbc.vemser.iShirts.model.enums.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Integer idProduto;

    private Categoria categoria;

    private String titulo;

    private String descricao;

    private String tecido;
}
