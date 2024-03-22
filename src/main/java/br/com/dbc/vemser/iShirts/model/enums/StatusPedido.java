package br.com.dbc.vemser.iShirts.model.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {
    EM_ANDAMENTO(0),
    A_CAMINHO(1),
    ENTREGUE(2),
    CANCELADO(3);

    private final int index;
    StatusPedido(int index) {
        this.index = index;
    }
}