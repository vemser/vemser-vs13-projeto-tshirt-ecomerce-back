package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MockPessoa {
    public static Pessoa retornarEntity(){
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(new Random().nextInt());
        pessoa.setAtivo("1");
        pessoa.setNome("Nome");
        pessoa.setSobrenome("Sobrenome");
        pessoa.setCpf("12345678901");
        pessoa.setCelular("11999999999");
        pessoa.setDataNascimento(new Date());
        pessoa.setPreferencia("Preferencia");
        pessoa.setIdUsuario(1);
        return pessoa;
    }
    public static List<Pessoa> retornarListaPessoaEntity(){
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(retornarEntity());
        pessoas.add(retornarEntity());
        return pessoas;
    }
    public static PessoaCreateDTO retornarPessoaCreateDTO(){
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setNome("Nome");
        pessoaCreateDTO.setSobrenome("Sobrenome");
        pessoaCreateDTO.setCpf("12345678901");
        pessoaCreateDTO.setCelular("11999999999");
        pessoaCreateDTO.setDataNascimento(new Date());
        pessoaCreateDTO.setPreferencia("Preferencia");
        pessoaCreateDTO.setAtivo("Ativo");
        return pessoaCreateDTO;
    }
    public static PessoaUpdateDTO retornarPessoaUpdateDTO(){
        PessoaUpdateDTO pessoaUpdateDTO = new PessoaUpdateDTO();
        pessoaUpdateDTO.setNome("Nome Atualizado");
        pessoaUpdateDTO.setSobrenome("Sobrenome Atualizado");
        pessoaUpdateDTO.setCpf("12345678901");
        pessoaUpdateDTO.setCelular("11999999999");
        pessoaUpdateDTO.setDataNascimento(new Date());
        pessoaUpdateDTO.setPreferencia("Preferencia Atualizada");
        pessoaUpdateDTO.setAtivo("Ativo");
        return pessoaUpdateDTO;
    }
}