package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.iShirts.model.Endereco;
import br.com.dbc.vemser.iShirts.model.Pessoa;

import java.sql.Timestamp;
import java.util.Random;

public class MockEndereco {
    public static Endereco retornaEntity(){
        Pessoa pessoa = MockPessoa.retornarEntity();
        return new Endereco(new Random().nextInt(), pessoa, pessoa.getIdPessoa(), "Rua teste", 1, "Complemento teste", "Referencia teste",
        "Bairro teste", "Cidade teste", "00000-000", "Estado teste", "Pais teste", new Timestamp(1), new Timestamp(1));
    }
    public static EnderecoDTO retornaDTO(){
        return new EnderecoDTO(new Random().nextInt(), MockPessoa.retornarEntity().getIdPessoa(), "Rua teste", 1, "Complemento teste", "Referencia teste",
                "Bairro teste", "Cidade teste", "00000-000", "Estado teste", "Pais teste", new Timestamp(1), new Timestamp(1));
    }
    public static EnderecoCreateDTO retornaCreate(){
        return new EnderecoCreateDTO(new Random().nextInt(),"Rua teste", 1, "Complemento teste", "Referencia teste",
                "Bairro teste", "00000-000", "Cidade teste", "Estado teste", "Pais teste", new Timestamp(1), new Timestamp(1));
    }
}
