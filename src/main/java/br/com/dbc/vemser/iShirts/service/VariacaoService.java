package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.VariacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VariacaoService {

    private final VariacaoRepository variacaoRepository;
    private final ObjectMapper objectMapper;

    public VariacaoDTO criarVariacao(VariacaoDTO variacaoDTO){
        Variacao variacaoEntity = objectMapper.convertValue(variacaoDTO, Variacao.class);
        variacaoEntity.setAtivo("1");
        Variacao variacaoSalva = variacaoRepository.save(variacaoEntity);

        return objectMapper.convertValue(variacaoSalva, VariacaoDTO.class);
    }

    public VariacaoDTO listarPorID(Integer id) throws Exception {
        Variacao variacao = variacaoRepository.findById(id).orElseThrow(() -> new Exception("Variacao não encontrada"));
        return objectMapper.convertValue(variacao, VariacaoDTO.class);
    }

    public List<VariacaoDTO> listarVariacoes() {
        List<Variacao> variacoes = variacaoRepository.findAll();
        return variacoes.stream()
                .map(variacao -> objectMapper.convertValue(variacao, VariacaoDTO.class))
                .toList();
    }

    public VariacaoDTO editarVariacao(Integer id, VariacaoDTO variacaoDTO) throws Exception {
        Variacao variacaoExistente = variacaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Variacao não encontrada"));

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

    public void deletarVariacao(Integer id) throws Exception {
        Variacao variacao = variacaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Variacao não encontrada"));

        variacaoRepository.delete(variacao);
    }

    public void desativarVariacao(Integer id) throws Exception {
        Variacao variacao = variacaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Variacao não encontrada"));

        variacao.setAtivo("0");
        variacaoRepository.save(variacao);
    }

}
