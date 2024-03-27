package br.com.dbc.vemser.iShirts.dto.pedido;

import br.com.dbc.vemser.iShirts.model.Carrinho;
import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {

    @NotNull
    private MetodoPagamento metodoPagamento;
    private Integer idCupom;

}
