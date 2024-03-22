package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.model.enums.Categoria;
import org.aspectj.weaver.ast.Var;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockVariacao {
    public static Variacao retornarEntity() throws IOException {
        Variacao variacao = new Variacao();
        variacao.setIdVariacao(new Random().nextInt());
        variacao.setProduto(retornarProduto());
        variacao.setFotos(MockFoto.retornarLista());
        variacao.setSku("SKU da variacao");
        variacao.setCor("Cor da variacao");
        variacao.setTamanho("Tamanho da variacao");
        variacao.setPreco(100.00);
        variacao.setTaxaDesconto(10);
        variacao.setAtivo("S");
        variacao.setCriado(new Timestamp(System.currentTimeMillis()));
        variacao.setEditado(new Timestamp(System.currentTimeMillis()));
        return  variacao;
    }

    public static List<Variacao> retornarLista() throws IOException {
        List<Variacao> variacoes = new ArrayList<>();
        variacoes.add(retornarEntity());
        variacoes.add(retornarEntity());
        return variacoes;
    }

    public static VariacaoDTO retornarVariacaoDTO(Variacao variacao) throws IOException {
        VariacaoDTO variacaoDTO = new VariacaoDTO();
        variacaoDTO.setIdVariacao(variacao.getIdVariacao());
        variacaoDTO.setFoto(MockFoto.retornarLista());
        variacaoDTO.setSku(variacao.getSku());
        variacaoDTO.setCor(variacao.getCor());
        variacaoDTO.setTamanho(variacao.getTamanho());
        variacaoDTO.setPreco(variacao.getPreco());
        variacaoDTO.setTaxaDesconto(variacao.getTaxaDesconto());
        variacaoDTO.setAtivo(variacao.getAtivo());
        variacaoDTO.setCriado(variacao.getCriado());
        variacaoDTO.setEditado(variacao.getEditado());
        return variacaoDTO;
    }

    private static Produto retornarProduto() throws IOException {
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setCategoria(Categoria.Outros);
        produto.setTitulo("Blusa de frio");
        produto.setDescricao("Uma blusa que te esquenta");
        produto.setTecido("Algodão");
        produto.setAtivo("S");
        produto.setCriado(new Timestamp(System.currentTimeMillis()));
        produto.setEditado(new Timestamp(System.currentTimeMillis()));
        produto.setVariacaoList(null);
        return produto;
    }
}
