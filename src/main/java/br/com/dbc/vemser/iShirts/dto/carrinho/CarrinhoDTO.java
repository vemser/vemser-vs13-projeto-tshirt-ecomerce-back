package br.com.dbc.vemser.iShirts.dto.carrinho;

import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoDTO {
    private Integer idCarrinho;
    private Integer idUsuario;
    private List<ItemDTO> itens = new ArrayList<>();
    private BigDecimal total;
}
