package br.com.dbc.vemser.iShirts.model.enums;

import lombok.Getter;

@Getter
public enum Ativo {
    INATIVO(0),
    ATIVO(1);

    private final int index;

    Ativo(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
