package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public Page<ProdutoDTO> listarProdutos(Pageable page){
        Page<Produto> listaProdutos = produtoRepository.findAllByAtivo(page, "1");
        return listaProdutos.map(produto -> objectMapper.convertValue(produto, ProdutoDTO.class));
    }

    public ProdutoDTO listarPorID(Integer id) throws Exception {
        Produto produto = produtoRepository.findById(id).get();
        if(produto.getAtivo().equals("0")){
            throw new Exception();
        }
        return objectMapper.convertValue(produto, ProdutoDTO.class);
    }

    public ProdutoDTO criarProduto(ProdutoCreateDTO produto){
        Produto produtoEntity = objectMapper.convertValue(produto, Produto.class);
        produtoEntity.setAtivo("1");
        produtoEntity.setCategoria(produto.getCategoria());
        Produto produtoSalvo = produtoRepository.save(produtoEntity);

        return objectMapper.convertValue(produtoSalvo, ProdutoDTO.class);
    }

    public ProdutoDTO editarProduto(ProdutoCreateDTO produto, Integer id){
        Produto produtoEntity = produtoRepository.findById(id).get();
        produtoEntity.setTitulo(produto.getTitulo());
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setCategoria(produto.getCategoria());
        produtoEntity.setTecido(produto.getTecido());
        Produto produtoSalvo = produtoRepository.save(produtoEntity);

        return objectMapper.convertValue(produtoSalvo, ProdutoDTO.class);
    }

    public String deletarProduto(Integer id){
        Produto produto = produtoRepository.findById(id).get();
        produto.setAtivo("0");
        produtoRepository.save(produto);

        return "Produto deletado com sucesso";
    }

}
