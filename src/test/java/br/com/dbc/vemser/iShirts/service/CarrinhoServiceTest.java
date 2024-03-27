package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemUpdateQuantidadeDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Carrinho;
import br.com.dbc.vemser.iShirts.model.Item;
import br.com.dbc.vemser.iShirts.repository.CarrinhoRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockCarrinho;
import br.com.dbc.vemser.iShirts.service.mocks.MockItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarrinhoService - Test")
class CarrinhoServiceTest {
    @Mock
    private  CarrinhoRepository carrinhoRepository;
    @Mock
    private  ItemService itemService;
    @Mock
    private  UsuarioService usuarioService;
    @InjectMocks
    private CarrinhoService carrinhoService;
    @Test
    void buscarCarrinhoPorId() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(carrinhoRepository.findById(carrinho.getIdCarrinho())).thenReturn(Optional.of(carrinho));

        Carrinho carrinhoResponse = carrinhoService.buscarCarrinhoPorId(carrinho.getIdCarrinho());

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho, carrinhoResponse);

    }
    @Test
    void naoDevebuscarCarrinhoPorIdInvalido() throws IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        assertThrows(RegraDeNegocioException.class, () -> {
            carrinhoService.buscarCarrinhoPorId(carrinho.getIdCarrinho());
        }, "Carrinho não encontrado com o ID: " + carrinho.getIdCarrinho());
    }

    @Test
    void buscarCarrinhoUsuarioLogado() throws RegraDeNegocioException, IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);

        Carrinho carrinhoResponse = carrinhoService.buscarCarrinhoUsuarioLogado();

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho, carrinhoResponse);
    }

    @Test
    void buscarCarrinhoUsuarioLogadoPrimeiraVez() throws RegraDeNegocioException, IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.save(any())).thenReturn(carrinho);

        Carrinho carrinhoResponse = carrinhoService.buscarCarrinhoUsuarioLogado();

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho, carrinhoResponse);
    }

    @Test
    void criarCarrinho() throws RegraDeNegocioException, IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();
        CarrinhoCreateDTO carrinhoCreateDTO = MockCarrinho.retornaCarrinhoCreate();

        when(itemService.criarItens(carrinhoCreateDTO.getItens())).thenReturn(carrinho.getItens());
        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.criarCarrinho(carrinhoCreateDTO);

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());
    }

    @Test
    void atualizarCarrinho() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.atualizarCarrinho();

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());

    }

    @Test
    void calculoBrutoTotal() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        BigDecimal total = carrinhoService.calcularValorBrutoTotal(carrinho);

        assertNotNull(total);
        assertEquals(total, BigDecimal.valueOf(1000.0));


    }

    @Test
    void deleteCarrinho() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(carrinhoRepository.findById(carrinho.getIdCarrinho())).thenReturn(Optional.of(carrinho));

        carrinhoService.deleteCarrinho(carrinho.getIdCarrinho());

        verify(carrinhoRepository, times(1)).delete(carrinho);
    }

    @Test
    void adicionarItemCarrinho() throws RegraDeNegocioException, IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();
        ItemCreateDTO itemCreateDTO = MockItem.retornarItemCreateDTO();
        Item item = MockItem.retornarEntitty();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(itemService.criarItem(itemCreateDTO)).thenReturn(item);
        when(itemService.salvarItem(item)).thenReturn(item);
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.adicionarItemCarrinho(itemCreateDTO);

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());

    }

    @Test
    void removerItemCarrinho() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();
        Integer idItem = carrinho.getItens().get(0).getIdItem();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(itemService.buscarItemPorId(idItem)).thenReturn(carrinho.getItens().get(0));
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.removerItemCarrinho(idItem);

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());
        assertEquals(1, carrinho.getItens().size());

    }

    @Test
    void limparCarrinho() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);

        carrinhoService.limparCarrinho();

        verify(carrinhoRepository, times(1)).save(carrinho);
        assertEquals(0, carrinho.getItens().size());
        assertEquals(BigDecimal.ZERO, carrinho.getTotal());
    }

    @Test
    void limparCarrinhoPedidoFeito() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);

        carrinhoService.limparCarrinhoPedidoFeito();

        verify(carrinhoRepository, times(1)).save(carrinho);
        assertEquals(0, carrinho.getItens().size());
        assertEquals(BigDecimal.ZERO, carrinho.getTotal());
    }

    @Test
    void atualizaQuantidadeItem() throws RegraDeNegocioException, IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();
        Item item = carrinho.getItens().get(0);
        Integer idItem = carrinho.getItens().get(0).getIdItem();
        Integer quantidade = new Random().nextInt();


        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.atualizaQuantidadeItem(idItem,  new ItemUpdateQuantidadeDTO(quantidade));

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());
        assertEquals(item.getQuantidade(), quantidade);


    }

    @Test
    void buscarCarrinho() throws IOException, RegraDeNegocioException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        when(usuarioService.buscarUsuarioLogadoEntity()).thenReturn(carrinho.getUsuario());
        when(carrinhoRepository.findByUsuario(carrinho.getUsuario())).thenReturn(carrinho);
        when(itemService.converterDTO(carrinho.getItens())).thenReturn(MockItem.retornarListaItemDTO());

        CarrinhoDTO carrinhoResponse = carrinhoService.buscarCarrinho();

        assertNotNull(carrinhoResponse);
        assertEquals(carrinho.getUsuario().getIdUsuario(), carrinhoResponse.getIdUsuario());
        assertEquals(carrinho.getIdCarrinho(), carrinhoResponse.getIdCarrinho());

    }

    @Test
    void calcularTotal() throws IOException {
        Carrinho carrinho = MockCarrinho.retornarEntity();

        carrinhoService.calcularTotal(carrinho);

        assertEquals(0, BigDecimal.valueOf(900.00).compareTo(carrinho.getTotal()));

    }
}