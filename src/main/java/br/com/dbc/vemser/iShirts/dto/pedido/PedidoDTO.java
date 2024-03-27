package br.com.dbc.vemser.iShirts.dto.pedido;

import br.com.dbc.vemser.iShirts.model.Item;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer idPedido;

    private MetodoPagamento metodoPagamento;

    private StatusPedido status;

    private BigDecimal totalBruto;

    private BigDecimal desconto;

    private BigDecimal totalLiquido;

    private Pessoa pessoa;

    private List<Item> itens;


    private Date criado;



}
