package br.com.dbc.vemser.iShirts.dto.pedido;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer idpedido;

    private MetodoPagamento metodoPagamento;

    private StatusPedido status;

    private String totalBruto;

    private String desconto;

    private Date totalLiquido;

    private Pessoa pessoa;
}
