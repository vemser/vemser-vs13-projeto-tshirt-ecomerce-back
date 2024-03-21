package br.com.dbc.vemser.iShirts.dto.carrinho;

import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoCreateDTO {
    @NotNull
    private List<ItemCreateDTO> itens = new ArrayList<>();
}
