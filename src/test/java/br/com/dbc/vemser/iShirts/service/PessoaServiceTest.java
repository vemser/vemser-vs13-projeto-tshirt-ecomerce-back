package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private ObjectMapper objectMapper;


    @Mock
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        pessoaService = new PessoaService(pessoaRepository, objectMapper);
    }

    @Tag("Teste para criar uma Pessoa")
    @Test
    public void testarCadastrarPessoa() throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setNome("Nome");
        pessoaCreateDTO.setSobrenome("Sobrenome");
        pessoaCreateDTO.setCpf("12345678901");
        pessoaCreateDTO.setCelular("11999999999");
        pessoaCreateDTO.setDataNascimento(new Date());
        pessoaCreateDTO.setPreferencia("Preferencia");
        pessoaCreateDTO.setAtivo("Ativo");
        pessoaCreateDTO.setIdUsuario(1);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());
        pessoa.setIdUsuario(pessoaCreateDTO.getIdUsuario());

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoaCreateDTO);

        assertEquals(pessoa, pessoaCadastrada);
    }


}