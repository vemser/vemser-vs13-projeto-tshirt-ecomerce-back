package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Cupom;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.repository.ProdutoRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockProduto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProdutoService - Test")
class ProdutoServiceTest {

    @Mock
    private  ProdutoRepository produtoRepository;
    @Mock
    private  ObjectMapper objectMapper;
    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void listarProdutos() throws IOException {
        Pageable pageable = PageRequest.of(0, 10);
        List<Produto> produtos = MockProduto.retornarListaProduto();
        List<ProdutoDTO> produtosDTO = MockProduto.retornarListaProdutoDTO();
        Page<Produto> produtosPageEntity = new PageImpl<>(produtos, pageable, produtos.size());

        when(produtoRepository.findAllByAtivo(pageable, "1")).thenReturn(produtosPageEntity);
        when(objectMapper.convertValue(any(Produto.class), eq(ProdutoDTO.class))).thenReturn(produtosDTO.get(0));

        Page<ProdutoDTO> produtosResponse = produtoService.listarProdutos(pageable);

        assertNotNull(produtosResponse);
        assertEquals(produtosPageEntity.getSize(), produtosResponse.getSize());
        assertEquals(produtosDTO.get(0), produtosResponse.getContent().get(0));
    }

    @Test
    void listarPorID() throws IOException, RegraDeNegocioException {
        Produto produto = MockProduto.retornarEntity();
        ProdutoDTO produtoDTO = MockProduto.retornarDTO(produto);

        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));
        when(objectMapper.convertValue(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO produtoResponse = produtoService.listarPorID(produto.getIdProduto());


        assertNotNull(produtoResponse);
        assertEquals(produtoDTO, produtoResponse);
    }


    @Test
    void naoDeveListaProdutoNaoEncontrado() throws IOException, RegraDeNegocioException {
        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.listarPorID(new Random().nextInt());
        }, "Produto não encontrado");

    }


    @Test
    void naoDeveListaProdutoInativo() throws IOException, RegraDeNegocioException {
        Produto produto = MockProduto.retornarEntity();
        produto.setAtivo("0");

        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));

        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.listarPorID(produto.getIdProduto());
        }, "Produto não encontrado");
    }

    @Test
    void criarProduto() throws IOException {
        Produto produto = MockProduto.retornarEntity();
        ProdutoCreateDTO produtoCreateDTO = MockProduto.retornarProdutoCreate();
        ProdutoDTO produtoDTO = MockProduto.retornarDTO(produto);

        when(objectMapper.convertValue(produtoCreateDTO, Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(objectMapper.convertValue(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO produtoResponse = produtoService.criarProduto(produtoCreateDTO);

        assertNotNull(produtoResponse);
        assertEquals(produtoDTO, produtoResponse);
    }

    @Test
    void editarProduto() throws IOException, RegraDeNegocioException {
        Produto produto = MockProduto.retornarEntity();
        ProdutoCreateDTO produtoCreateDTO = MockProduto.retornarProdutoCreate();
        ProdutoDTO produtoDTO = MockProduto.retornarDTO(produto);

        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(objectMapper.convertValue(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO produtoResponse = produtoService.editarProduto(produtoCreateDTO, produto.getIdProduto());

        assertNotNull(produtoResponse);
        assertEquals(produtoDTO, produtoResponse);
    }

    @Test
    void deletarProduto() throws IOException, RegraDeNegocioException {
        Produto produto = MockProduto.retornarEntity();

        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));

        String response = produtoService.deletarProduto(produto.getIdProduto());

        verify(produtoRepository, times(1)).save(produto);
        assertEquals("Produto deletado com sucesso", response);
        assertEquals("0", produto.getAtivo());
    }

}