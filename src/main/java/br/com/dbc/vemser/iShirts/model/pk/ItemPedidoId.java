package br.com.dbc.vemser.iShirts.model.pk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ItemPedidoId implements Serializable {
    private Integer idCarrinho;
    private Integer idItem;

}