package br.com.dbc.vemser.iShirts.model;

import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO")
    @SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "ID_PEDIDO")
    private Integer idPedido;

    @Column(name = "METODO_PAGAMENTO")
    private MetodoPagamento metodoPagamento;

    @Column(name = "STATUS")
    private StatusPedido status;

    @Column(name = "TOTAL_BRUTO")
    private BigDecimal totalBruto;

    @Column(name = "DESCONTO")
    private BigDecimal desconto;

    @Column(name = "TOTAL_LIQUIDO")
    private BigDecimal totalLiquido;

    @Column(name = "CRIADO", columnDefinition = "TIMESTAMP")
    private Date criado;

    @Column(name = "EDITADO", columnDefinition = "TIMESTAMP")
    private Date editado;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    private Pessoa pessoa;

    @ManyToMany
    @JoinTable(name = "ITEM_PEDIDO",
            joinColumns = @JoinColumn(name = "ID_PEDIDO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ITEM"))
    private List<Item> itens;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupom", referencedColumnName = "id_cupom")
    private Cupom cupom;
}
