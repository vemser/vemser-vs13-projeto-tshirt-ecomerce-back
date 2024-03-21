package br.com.dbc.vemser.iShirts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToOne
    @JoinColumn(name = "ID_USUARIO", unique = true)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "ITEM_CARRINHO",
            joinColumns = @JoinColumn(name = "ID_CARRINHO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ITEM"))
    private List<Item> itens = new ArrayList<>();
}