package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Item;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.ItemRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockItem;
import br.com.dbc.vemser.iShirts.service.mocks.MockVariacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ItemService - Test")
class ItemServiceTest {
    @Mock
    private  ItemRepository itemRepository;
    @Mock
    private  ObjectMapper objectMapper;
    @Mock
    private  VariacaoService variacaoService;
    @InjectMocks
    private ItemService itemService;

    @Test
    void buscarItemPorId() throws IOException, RegraDeNegocioException {
        Item item = MockItem.retornarEntitty();

        when(itemRepository.findById(item.getIdItem())).thenReturn(Optional.of(item));

        Item itemResponse = itemService.buscarItemPorId(item.getIdItem());

        assertEquals(item, itemResponse);
    }

    @Test
    void naoDeveObterItemInexistente() throws IOException, RegraDeNegocioException {
        Item item = MockItem.retornarEntitty();

        assertThrows(RegraDeNegocioException.class, () -> {
            itemService.buscarItemPorId(item.getIdItem());
        }, "Item não encontrado com o ID: " + item.getIdItem());
    }


    @Test
    void salvarItem() throws IOException {
        Item item = MockItem.retornarEntitty();

        when(itemRepository.save(item)).thenReturn(item);

        Item itemResponse = itemService.salvarItem(item);

        verify(itemRepository, times(1)).save(item);
        assertEquals(item, itemResponse);
    }

    @Test
    void converterDTO() throws IOException {
        List<Item> itens = MockItem.retornarListaItens();
        when(objectMapper.convertValue(any(Item.class), eq(ItemDTO.class))).thenReturn(MockItem.retornarItemDTO(MockItem.retornarEntitty()));

        List<ItemDTO> itensResponse = itemService.converterDTO(itens);

        assertNotNull(itensResponse);
        assertEquals(itens.size(), itensResponse.size());
    }


    @Test
    void criarItens() throws IOException, RegraDeNegocioException {
        Variacao variacao = MockVariacao.retornarEntity();
        Item item = MockItem.retornarEntitty();
        item.setVariacao(variacao);

        when(variacaoService.buscarPorId(anyInt())).thenReturn(variacao);
        when(objectMapper.convertValue(any(ItemCreateDTO.class), eq(Item.class))).thenReturn(item);
        when(itemRepository.saveAll(any())).thenReturn(MockItem.retornarListaItens());

        List<Item> itensResponse = itemService.criarItens(MockItem.retornarListaItemCreate());

        assertNotNull(itensResponse);
        assertEquals(itensResponse.size(), 2);
    }

    @Test
    void atualizarItens() throws IOException {
        List<Item> itens = MockItem.retornarListaItens();
        itemService.atualizarItens(itens);
        verify(itemRepository, times(2)).save(any());
    }

    @Test
    void delete() throws IOException {
        Item item = MockItem.retornarEntitty();

        itemService.delete(item);

        verify(itemRepository, times(1)).delete(item);
    }


}