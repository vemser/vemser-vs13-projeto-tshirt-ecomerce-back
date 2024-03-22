package br.com.dbc.vemser.iShirts.dto.item;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Integer idItem;
    private VariacaoDTO variacao;
    private Integer quantidade;
    private BigDecimal subTotal;
}
