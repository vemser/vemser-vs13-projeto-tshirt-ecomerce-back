package br.com.dbc.vemser.iShirts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ITEM")
@Table(name = "ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
    @SequenceGenerator(name = "SEQ_ITEM", sequenceName = "SEQ_ITEM", allocationSize = 1)
    @Column(name = "ID_ITEM")
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "id_variacao")
    private Variacao variacao;

    private int quantidade;
    private double subTotal;

}