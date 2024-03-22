package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.enums.Categoria;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

public class MockProduto {
    public static Produto retornarEntity() throws IOException {
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setCategoria(Categoria.Outros);
        produto.setTitulo("Blusa de frio");
        produto.setDescricao("Uma blusa que te esquenta");
        produto.setTecido("Algodão");
        produto.setAtivo("S");
        produto.setCriado(new Timestamp(System.currentTimeMillis()));
        produto.setEditado(new Timestamp(System.currentTimeMillis()));
        produto.setVariacaoList(MockVariacao.retornarLista());
        return produto;
    }
}
