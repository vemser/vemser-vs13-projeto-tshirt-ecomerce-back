package br.com.dbc.vemser.iShirts.model;

import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO")
    @SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "ID_PEDIDO")
    private Integer idpedido;

    @Column(name = "METODO_PAGAMENTO")
    private MetodoPagamento metodoPagamento;

    @Column(name = "STATUS")
    private StatusPedido status;

    @Column(name = "TOTAL_BRUTO")
    private String totalBruto;

    @Column(name = "DESCONTO")
    private String desconto;

    @Column(name = "TOTAL_LIQUIDO")
    private Date totalLiquido;

    @Column(name = "CRIADO", columnDefinition = "TIMESTAMP")
    private Date criado;

    @Column(name = "EDITADO", columnDefinition = "TIMESTAMP")
    private Date editado;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    private Pessoa pessoa;

}
