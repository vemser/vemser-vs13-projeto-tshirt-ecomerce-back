package br.com.dbc.vemser.iShirts.model;

import br.com.dbc.vemser.iShirts.model.pk.ItemCarrinhoId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ITEM_CARRINHO")
@Table(name = "ITEM_CARRINHO")
public class ItemCarrinho {
    @EmbeddedId
    private ItemCarrinhoId id;
    @ManyToOne
    @MapsId("idCarrinho")
    private Carrinho carrinho;

    @ManyToOne
    @MapsId("idItem")
    private Item item;

}
