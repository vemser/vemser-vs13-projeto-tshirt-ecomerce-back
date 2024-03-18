package br.com.dbc.vemser.iShirts.model;
import br.com.dbc.vemser.iShirts.model.enums.Categoria;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUTO")
    @SequenceGenerator(name = "SEQ_PRODUTO", sequenceName = "SEQ_PRODUTO", allocationSize = 1)
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "CATEGORIA")
    private Categoria categoria;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "TECIDO")
    private String tecido;

    @Column(name = "ATIVO")
    private String ativo;

    @Column(name = "CRIADO", updatable = false)
    @CreationTimestamp
    private Timestamp criado;

    @Column(name = "EDITADO")
    @UpdateTimestamp
    private Timestamp editado;
}
