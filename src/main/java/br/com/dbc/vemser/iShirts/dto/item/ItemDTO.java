package br.com.dbc.vemser.iShirts.dto.item;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Integer idItem;
    private VariacaoDTO variacao;
    private Integer quantidade;
    private double subTotal;
}
