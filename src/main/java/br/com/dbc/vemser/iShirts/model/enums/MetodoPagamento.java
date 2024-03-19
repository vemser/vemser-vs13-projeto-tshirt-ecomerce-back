package br.com.dbc.vemser.iShirts.model.enums;

import lombok.Getter;

@Getter
public enum MetodoPagamento {
    CARTAO(0),
    PIX(1),
    DINHEIRO(2);

    private final int index;
    MetodoPagamento(int index) {
        this.index = index;
    }
}


