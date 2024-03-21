package br.com.dbc.vemser.iShirts.model;

import br.com.dbc.vemser.iShirts.model.pk.ItemCarrinhoId;
import br.com.dbc.vemser.iShirts.model.pk.ItemPedidoId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ITEM_PEDIDO")
@Table(name = "ITEM_PEDIDO")
public class ItemPedido {
    @EmbeddedId
    private ItemPedidoId id;
    @ManyToOne
    @MapsId("idPedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("idItem")
    private Item item;

}