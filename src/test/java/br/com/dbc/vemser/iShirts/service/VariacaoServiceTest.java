package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.VariacaoRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockVariacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VariacaoServiceTest {

    @InjectMocks
    private VariacaoService variacaoService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private VariacaoRepository variacaoRepository;

    @Mock
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        variacaoService = new VariacaoService(variacaoRepository, objectMapper, produtoService);
    }

    @Test
    public void testarCriarVariacao() throws RegraDeNegocioException {
        VariacaoCreateDTO variacaoCreateDTO = MockVariacao.retornarVariacaoCreateDTO();

        Produto produto = MockVariacao.retornarProduto();

        when(variacaoRepository.save(any(Variacao.class))).thenReturn(MockVariacao.retornarVariacao());
        when(produtoService.buscarPorId(anyInt())).thenReturn(produto);

        Variacao variacao = variacaoService.criarVariacao(variacaoCreateDTO);

        assertEquals(variacao, variacao);



    }





}
