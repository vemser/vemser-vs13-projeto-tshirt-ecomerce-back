package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemDTO;
import br.com.dbc.vemser.iShirts.model.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockItem {
    public static Item retornarEntitty() throws IOException {
        Item item = new Item();
        item.setIdItem(new Random().nextInt());
        item.setQuantidade(5);
        item.setVariacao(MockVariacao.retornarEntity());
        calcularTotal(item);
        return item;
    }

    public static ItemCreateDTO retornarItemCreateDTO(){
        ItemCreateDTO itemCreateDTO = new ItemCreateDTO(new Random().nextInt(), 5);
        return itemCreateDTO;
    }

    public static List<Item> retornarListaItens() throws IOException {
        List<Item> itens = new ArrayList<>();
        itens.add(retornarEntitty());
        itens.add(retornarEntitty());
        return itens;
    }
    public static List<ItemCreateDTO> retornarListaItemCreate(){
        List<ItemCreateDTO> itens = new ArrayList<>();
        itens.add(retornarItemCreateDTO());
        itens.add(retornarItemCreateDTO());
        return itens;
    }
    public static ItemDTO retornarItemDTO(Item item) throws IOException {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setIdItem(item.getIdItem());
            itemDTO.setQuantidade(itemDTO.getQuantidade());
            itemDTO.setSubTotal(item.getSubTotal());
             itemDTO.setVariacao(MockVariacao.retornarVariacaoDTO(item.getVariacao()));
            return itemDTO;
    }
    public static List<ItemDTO> retornarListaItemDTO() throws IOException {
        List<ItemDTO> itens = new ArrayList<>();
        itens.add(retornarItemDTO(retornarEntitty()));
        itens.add(retornarItemDTO(retornarEntitty()));
        return itens;
    }

    private static void calcularTotal(Item item) {
        BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
        BigDecimal preco = BigDecimal.valueOf(item.getVariacao().getPreco());
        BigDecimal taxaDesconto = BigDecimal.valueOf(item.getVariacao().getTaxaDesconto());
        BigDecimal taxaDescontoDecimal = taxaDesconto.divide(BigDecimal.valueOf(100));

        BigDecimal subTotal = quantidade
                .multiply(preco.subtract(preco.multiply(taxaDescontoDecimal)));
        item.setSubTotal(subTotal);
    }
}
