package br.com.dbc.vemser.iShirts.dto.pedido;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateDTO {

    @NotNull
    private MetodoPagamento metodoPagamento;
    @NotNull
    private StatusPedido status;
}
