package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.model.Produto;
import br.com.dbc.vemser.iShirts.model.Variacao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Random;

public class MockVariacao {

    public static Variacao retornarVariacao() {
        Variacao variacao = new Variacao();
        variacao.setIdVariacao(new Random().nextInt());;
        variacao.setSku("12345678901");
        variacao.setCor("Cor");
        variacao.setTamanho("Tamanho");
        variacao.setPreco(10.0);
        variacao.setTaxaDesconto(0);
        variacao.setAtivo("1");
        variacao.setCriado(Timestamp.valueOf("2024-03-20 16:21:56.335"));
        variacao.setEditado(Timestamp.valueOf("2024-03-20 16:21:56.335"));
        return variacao;
    }

    public static VariacaoCreateDTO retornarVariacaoCreateDTO() {
        VariacaoCreateDTO variacaoCreateDTO = new VariacaoCreateDTO();
        variacaoCreateDTO.setSku("CAM-BRA-GG-BSC-001");
        variacaoCreateDTO.setCor("Branco");
        variacaoCreateDTO.setTamanho("GG");
        variacaoCreateDTO.setPreco(BigDecimal.valueOf(45.0));
        variacaoCreateDTO.setTaxaDesconto(0);
        variacaoCreateDTO.setAtivo("1");
        variacaoCreateDTO.setIdProduto(1);
        return variacaoCreateDTO;
    }

    public static Produto retornarProduto() {
        Produto produto = new Produto();
        produto.setIdProduto(1);
        produto.setTitulo("Camiseta Básica");
        produto.setDescricao("Camiseta Básica");
        produto.setTecido("Algodão");
        produto.setAtivo("1");
        produto.setCriado(Timestamp.valueOf("2024-03-20 16:21:56.335"));
        produto.setEditado(Timestamp.valueOf("2024-03-20 16:21:56.335"));
        return produto;
    }




}
