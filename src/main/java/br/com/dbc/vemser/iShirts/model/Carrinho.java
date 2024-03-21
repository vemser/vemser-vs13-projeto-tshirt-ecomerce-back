package br.com.dbc.vemser.iShirts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CARRINHO")
@Table(name = "CARRINHO")
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARRINHO")
    @SequenceGenerator(name = "SEQ_CARRINHO", sequenceName = "SEQ_CARRINHO", allocationSize = 1)
    @Column(name = "ID_CARRINHO")
    private Integer idCarrinho;

    @OneToMany(mappedBy = "carrinho")
    private List<ItemCarrinho> itensCarrinho;
}