package br.com.dbc.vemser.iShirts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VARIACAO")
public class Variacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VARIACAO")
    @SequenceGenerator(name = "SEQ_VARIACAO",sequenceName = "SEQ_VARIACAO", allocationSize = 1)
    @Column(name = "ID_VARIACOES")
    //@OneToMany(mappedBy = "idVariacao")
    private Integer idVariacoes;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO")
    private Produto produto;

    @OneToMany(mappedBy = "idVariacao")
    private Foto foto;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "COR")
    private String cor;

    @Column(name = "TAMANHO")
    private String tamanho;

    @Column(name = "PRECO")
    private Double preco;

    @Column(name = "TAXA_DESCONTO")
    private Integer taxaDesconto;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "CRIADO")
    private Timestamp criado;

    @UpdateTimestamp
    @Column(name = "EDITADO")
    private Timestamp editado;

}
