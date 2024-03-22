package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.enums.Categoria;
import org.springframework.security.core.parameters.P;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockProduto {
    public static Produto retornarEntity() throws IOException {
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setCategoria(Categoria.Outros);
        produto.setTitulo("Blusa de frio");
        produto.setDescricao("Uma blusa que te esquenta");
        produto.setTecido("Algodão");
        produto.setAtivo("1");
        produto.setCriado(new Timestamp(System.currentTimeMillis()));
        produto.setEditado(new Timestamp(System.currentTimeMillis()));
        produto.setVariacaoList(MockVariacao.retornarLista());
        return produto;
    }

    public static List<Produto> retornarListaProduto() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(retornarEntity());
        produtos.add(retornarEntity());
        return produtos;
    }

    public static List<ProdutoDTO> retornarListaProdutoDTO() throws IOException {
        List<ProdutoDTO> produtos = new ArrayList<>();
        produtos.add(retornarDTO(retornarEntity()));
        produtos.add(retornarDTO(retornarEntity()));
        return produtos;
    }
    public static ProdutoDTO retornarDTO(Produto produto){
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setIdProduto(produto.getIdProduto());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setTitulo(produto.getTitulo());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setTecido(produto.getTecido());
        produtoDTO.setVariacaoList(produto.getVariacaoList());
        return produtoDTO;
    }


    public static ProdutoCreateDTO retornarProdutoCreate(){
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO();
        produtoCreateDTO.setTitulo("Blusa do verão");
        produtoCreateDTO.setDescricao("Uma blusa");
        produtoCreateDTO.setTecido("Algodão");
        produtoCreateDTO.setCategoria(Categoria.Outros);
        return produtoCreateDTO;
    }


}
