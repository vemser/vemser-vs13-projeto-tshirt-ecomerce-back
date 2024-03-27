package br.com.dbc.vemser.iShirts.dto.cupom;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CupomDTO {
    private Integer idCupom;
    private Integer codigo;
    private Double valor;
    private LocalDateTime validade;
    private Integer limiteUso;
    private Double valorMinimo;
}
