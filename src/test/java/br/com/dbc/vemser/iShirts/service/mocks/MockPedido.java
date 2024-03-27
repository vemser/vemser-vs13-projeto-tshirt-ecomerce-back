package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.model.Pedido;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.enums.MetodoPagamento;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MockPedido {

    public static Pedido retornarEntity() throws IOException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
        pedido.setCupom(MockCupom.retornarCupom());
        pedido.setPessoa(MockPessoa.retornarEntity());
        pedido.setTotalLiquido(MockCarrinho.retornarEntity().getTotal().subtract(BigDecimal.valueOf((pedido.getCupom().getValor()))));
        pedido.setDesconto(BigDecimal.valueOf(200));
        pedido.setTotalBruto(pedido.getTotalLiquido().add(pedido.getDesconto()));
        pedido.setEditado(new Timestamp(System.currentTimeMillis()));
        pedido.setCriado(new Timestamp(System.currentTimeMillis()));
        pedido.setItens(MockCarrinho.retornarEntity().getItens());

        return  pedido;
    }

    public static List<Pedido> retornarListaPedidoEntity() throws IOException {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(retornarEntity());
        pedidos.add(retornarEntity());
        pedidos.add(retornarEntity());
        pedidos.add(retornarEntity());
        return pedidos;
    }

    public static PedidoDTO retornarPedidoDTO() throws IOException {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdPedido(1);
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
        pedido.setPessoa(MockPessoa.retornarEntity());
        pedido.setTotalLiquido(MockCarrinho.retornarEntity().getTotal().subtract(BigDecimal.valueOf(MockCupom.retornarCupom().getValor())));
        pedido.setDesconto(BigDecimal.valueOf(200));
        pedido.setTotalBruto(pedido.getTotalLiquido().add(pedido.getDesconto()));
        pedido.setCriado(new Timestamp(System.currentTimeMillis()));
        pedido.setItens(MockCarrinho.retornarEntity().getItens());

        return  pedido;
    }

    public static List<PedidoDTO> retornarListaPedidoDTO() throws IOException {
        List<PedidoDTO> pedidos = new ArrayList<>();
        pedidos.add(retornarPedidoDTO());
        pedidos.add(retornarPedidoDTO());
        pedidos.add(retornarPedidoDTO());
        pedidos.add(retornarPedidoDTO());
        return pedidos;
    }

    public static PedidoCreateDTO retornarPedidoCreateDTO() {
        PedidoCreateDTO pedido = new PedidoCreateDTO();
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setIdCupom(MockCupom.retornarCupom().getIdCupom());
        return  pedido;
    }

    public static PedidoUpdateDTO retornarPedidoUpdateDTO() {
        PedidoUpdateDTO pedido = new PedidoUpdateDTO();
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);

        return  pedido;
    }
}
