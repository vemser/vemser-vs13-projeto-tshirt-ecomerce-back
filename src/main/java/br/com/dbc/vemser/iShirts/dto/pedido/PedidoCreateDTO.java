package br.com.dbc.vemser.iShirts.dto.pedido;

import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {

    private MetodoPagamento metodoPagamento;

    private String totalBruto;

    private String desconto;

    private Date totalLiquido;

    private Date criado;

}
