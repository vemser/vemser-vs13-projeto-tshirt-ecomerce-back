package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.model.Carrinho;
import br.com.dbc.vemser.iShirts.model.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class MockCarrinho {
    public static Carrinho retornarEntity() throws IOException {
        Carrinho carrinho = new Carrinho();
        carrinho.setIdCarrinho(new Random().nextInt());
        carrinho.setItens(MockItem.retornarListaItens());
        carrinho.setUsuario(MockUsuario.retornarEntity());
        calculaTotal(carrinho);
        return carrinho;
    }

    public static CarrinhoDTO retornarCarrinhoDTO(Carrinho carrinho) throws IOException {
        CarrinhoDTO carrinhoDTO = new CarrinhoDTO();
        carrinhoDTO.setItens(MockItem.retornarListaItemDTO());
        carrinhoDTO.setIdUsuario(carrinho.getUsuario().getIdUsuario());
        carrinhoDTO.setTotal(carrinho.getTotal());
        return carrinhoDTO;
    }

    public static CarrinhoCreateDTO retornaCarrinhoCreate(){
        CarrinhoCreateDTO carrinhoCreateDTO = new CarrinhoCreateDTO();
        carrinhoCreateDTO.setItens(MockItem.retornarListaItemCreate());
        return carrinhoCreateDTO;
    }

    private static void calculaTotal(Carrinho carrinho) {
        BigDecimal total = BigDecimal.ZERO;
        List<Item> itens = carrinho.getItens();

        for (Item item : itens) {
            BigDecimal subTotal = item.getSubTotal();
            total = total.add(subTotal);
        }

        carrinho.setTotal(total);
    }
}
