package br.com.dbc.vemser.iShirts.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "CUPOM_DESCONTO")
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_CUPOM")
    @SequenceGenerator(name = "SEQ_CUPOM",sequenceName = "SEQ_CUPOM", allocationSize = 1)
    @Column(name = "ID_CUPOM")
    private Integer idCupom;

    @Column(name = "CODIGO")
    private Integer codigo;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "VALIDADE")
    private LocalDateTime validade;

    @Column(name = "LIMITE_USO")
    private Integer limiteUso;

    @Column(name = "VALOR_MINIMO")
    private Double valorMinimo;
}
