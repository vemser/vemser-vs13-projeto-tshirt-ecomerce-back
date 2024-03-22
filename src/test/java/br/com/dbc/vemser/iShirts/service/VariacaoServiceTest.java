package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import br.com.dbc.vemser.iShirts.repository.VariacaoRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockVariacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VariacaoServiceTest {

    @InjectMocks
    private VariacaoService variacaoService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private VariacaoRepository variacaoRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void deveCriarVariacao() throws RegraDeNegocioException {
        VariacaoCreateDTO variacaoCreateDTO = MockVariacao.retornarVariacaoCreateDTO();
        Produto produto = MockVariacao.retornarProduto();
        Variacao variacao = MockVariacao.retornarVariacao();

        when(produtoService.buscarPorId(variacaoCreateDTO.getIdProduto())).thenReturn(produto);
        when(objectMapper.convertValue(variacaoCreateDTO, Variacao.class)).thenReturn(variacao);
        when(variacaoRepository.save(variacao)).thenReturn(variacao);
        when(objectMapper.convertValue(variacao, VariacaoDTO.class)).thenReturn(new VariacaoDTO());

        VariacaoDTO variacaoDTO = variacaoService.criarVariacao(variacaoCreateDTO);

        verify(produtoService, times(1)).buscarPorId(variacaoCreateDTO.getIdProduto());
        verify(objectMapper, times(1)).convertValue(variacaoCreateDTO, Variacao.class);
        verify(variacaoRepository, times(1)).save(variacao);
        verify(objectMapper, times(1)).convertValue(variacao, VariacaoDTO.class);
    }

    @Test
    public void deveListarPorID() throws Exception {
        Integer id = 1;
        Variacao variacao = MockVariacao.retornarVariacao();
        VariacaoDTO variacaoDTO = new VariacaoDTO();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        when(objectMapper.convertValue(variacao, VariacaoDTO.class)).thenReturn(variacaoDTO);

        VariacaoDTO result = variacaoService.listarPorID(id);

        verify(variacaoRepository, times(1)).findById(id);
        verify(objectMapper, times(1)).convertValue(variacao, VariacaoDTO.class);
        assertEquals(variacaoDTO, result);
    }

    @Test
    public void deveListarVariacoes() {
        Pageable pageable = PageRequest.of(0, 5);
        Variacao variacao = MockVariacao.retornarVariacao();
        VariacaoDTO variacaoDTO = new VariacaoDTO();
        List<Variacao> variacoes = Arrays.asList(variacao);
        Page<Variacao> variacoesPage = new PageImpl<>(variacoes);

        when(variacaoRepository.findAll(pageable)).thenReturn(variacoesPage);
        when(objectMapper.convertValue(variacao, VariacaoDTO.class)).thenReturn(variacaoDTO);

        Page<VariacaoDTO> result = variacaoService.listarVariacoes(pageable);

        verify(variacaoRepository, times(1)).findAll(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(variacaoDTO, result.getContent().get(0));
    }

    @Test
    public void deveEditarVariacao() throws RegraDeNegocioException {
        Integer id = 1;
        Variacao variacaoExistente = MockVariacao.retornarVariacao();
        VariacaoDTO variacaoDTO = new VariacaoDTO();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacaoExistente));
        when(variacaoRepository.save(variacaoExistente)).thenReturn(variacaoExistente);
        when(objectMapper.convertValue(variacaoExistente, VariacaoDTO.class)).thenReturn(variacaoDTO);

        VariacaoDTO result = variacaoService.editarVariacao(id, variacaoDTO);

        verify(variacaoRepository, times(1)).findById(id);
        verify(variacaoRepository, times(1)).save(variacaoExistente);
        verify(objectMapper, times(1)).convertValue(variacaoExistente, VariacaoDTO.class);
        assertEquals(variacaoDTO, result);
    }

    @Test
    public void deveDeletarVariacao() throws RegraDeNegocioException {
        Integer id = 1;
        Variacao variacao = MockVariacao.retornarVariacao();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        doNothing().when(variacaoRepository).delete(variacao);

        variacaoService.deletarVariacao(id);

        verify(variacaoRepository, times(1)).findById(id);
        verify(variacaoRepository, times(1)).delete(variacao);
    }

    @Test
    public void deveDesativarVariacao() throws RegraDeNegocioException {
        Integer id = 1;
        Variacao variacao = MockVariacao.retornarVariacao();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        when(variacaoRepository.save(variacao)).thenReturn(variacao); // Changed this line

        variacaoService.desativarVariacao(id);

        verify(variacaoRepository, times(1)).findById(id);
        verify(variacaoRepository, times(1)).save(variacao);
        assertEquals(String.valueOf(Ativo.INATIVO.getIndex()), variacao.getAtivo());
    }

    @Test
    public void deveAtivarVariacao() throws RegraDeNegocioException {
        Integer id = 1;
        Variacao variacao = MockVariacao.retornarVariacao();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));
        when(variacaoRepository.save(variacao)).thenReturn(variacao);

        variacaoService.ativarVariacao(id);

        verify(variacaoRepository, times(1)).findById(id);
        verify(variacaoRepository, times(1)).save(variacao);
        assertEquals(String.valueOf(Ativo.ATIVO.getIndex()), variacao.getAtivo());
    }

    @Test
    public void deveBuscarPorId() throws RegraDeNegocioException {
        Integer id = 1;
        Variacao variacao = MockVariacao.retornarVariacao();

        when(variacaoRepository.findById(id)).thenReturn(Optional.of(variacao));

        Variacao result = variacaoService.buscarPorId(id);

        verify(variacaoRepository, times(1)).findById(id);
        assertEquals(variacao, result);
    }

}
