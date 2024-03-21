package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.VariacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VariacaoService {

    private final VariacaoRepository variacaoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoService produtoService;

    private static final String NAO_ENCONTRADO = "Variação não encontrada";

    public VariacaoDTO criarVariacao(VariacaoCreateDTO variacaoCreateDTO) throws RegraDeNegocioException {
        System.out.println("Passou aqui");
        Produto produto = produtoService.buscarPorId(variacaoCreateDTO.getIdProduto());


        Variacao variacaoEntity = objectMapper.convertValue(variacaoCreateDTO, Variacao.class);
        variacaoEntity.setProduto(produto);
        Variacao variacaoSalva = variacaoRepository.save(variacaoEntity);

        return objectMapper.convertValue(variacaoSalva, VariacaoDTO.class);
    }

    public VariacaoDTO listarPorID(Integer id) throws Exception {
        Variacao variacao = variacaoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Variacao não encontrada"));
        return objectMapper.convertValue(variacao, VariacaoDTO.class);
    }

    public Page<VariacaoDTO> listarVariacoes(Pageable pageable) {
        Page<Variacao> variacoesPage = variacaoRepository.findAll(pageable);
        return variacoesPage.map(variacao -> objectMapper.convertValue(variacao, VariacaoDTO.class));
    }

    public VariacaoDTO editarVariacao(Integer id, VariacaoDTO variacaoDTO) throws RegraDeNegocioException {
        Variacao variacaoExistente = variacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Variacao não encontrada"));

        variacaoExistente.setSku(variacaoDTO.getSku());
        variacaoExistente.setCor(variacaoDTO.getCor());
        variacaoExistente.setTamanho(variacaoDTO.getTamanho());
        variacaoExistente.setPreco(variacaoDTO.getPreco());
        variacaoExistente.setTaxaDesconto(variacaoDTO.getTaxaDesconto());
        variacaoExistente.setAtivo(variacaoDTO.getAtivo());
        variacaoExistente.setEditado(variacaoDTO.getEditado());

        Variacao variacaoAtualizada = variacaoRepository.save(variacaoExistente);

        return objectMapper.convertValue(variacaoAtualizada, VariacaoDTO.class);
    }

    public void deletarVariacao(Integer id) throws RegraDeNegocioException {
        Variacao variacao = variacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Variacao não encontrada"));

        variacaoRepository.delete(variacao);
    }

    public void desativarVariacao(Integer id) throws RegraDeNegocioException {
        Variacao variacao = variacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Variacao não encontrada"));

        variacao.setAtivo("0");
        variacaoRepository.save(variacao);
    }

    public Variacao buscarPorId(Integer id) throws RegraDeNegocioException {
        Variacao variacao = variacaoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException(NAO_ENCONTRADO));
        return variacao;
    }

}
