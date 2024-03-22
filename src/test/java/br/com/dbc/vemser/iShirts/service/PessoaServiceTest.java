package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockPessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @DisplayName("Teste para cadastrar uma Pessoa")
    @Test
    public void testarCadastrarPessoa() throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = MockPessoa.retornarPessoaCreateDTO();

        Pessoa pessoa = MockPessoa.retornarEntity();

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoaCreateDTO);

        assertEquals(pessoa, pessoaCadastrada);
    }

    @DisplayName("Teste_para_atualizar_uma_Pessoa")
    @Test
    public void testarAtualizarUmaPessoa() throws RegraDeNegocioException {
        PessoaUpdateDTO pessoaUpdateDTO = MockPessoa.retornarPessoaUpdateDTO();

        Pessoa pessoa = MockPessoa.retornarEntity();

        Pessoa pessoaExistente = MockPessoa.retornarEntity();
        when(pessoaRepository.findById(pessoaExistente.getIdPessoa())).thenReturn(Optional.of(pessoaExistente));

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(pessoaExistente.getIdPessoa(), pessoaUpdateDTO);

        assertEquals(pessoa, pessoaAtualizada);
    }

    @DisplayName("Teste_para_atualizar_uma_Pessoa_com_excecao")
    @Test
    public void testarAtualizarUmaPessoaComExcecao() {
        PessoaUpdateDTO pessoaUpdateDTO = new PessoaUpdateDTO();
        pessoaUpdateDTO.setNome("Nome");
        pessoaUpdateDTO.setSobrenome("Sobrenome");
        pessoaUpdateDTO.setCpf("12345678901");
        pessoaUpdateDTO.setCelular("11999999999");
        pessoaUpdateDTO.setDataNascimento(new Date());
        pessoaUpdateDTO.setPreferencia("Preferencia");
        pessoaUpdateDTO.setAtivo("Ativo");

        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setIdPessoa(1);
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoaExistente));

        when(pessoaRepository.save(any(Pessoa.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> {
            pessoaService.atualizarPessoa(1, pessoaUpdateDTO);
        });
    }

    @DisplayName("Teste_para_buscar_todas_as_Pessoas")
    @Test
    public void testarBuscarTodasPessoas() throws RegraDeNegocioException {
        Pageable pageable = PageRequest.of(0,10);

        List<Pessoa> pessoas = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Pessoa p = new Pessoa();
            p.setIdPessoa(i);
            pessoas.add(p);
        }

        Page<Pessoa> pessoaPage = new PageImpl<>(pessoas);

        when(pessoaRepository.findAll(any(Pageable.class))).thenReturn(pessoaPage);

        Page<Pessoa> result = pessoaService.buscarTodasPessoas(pageable);

        assertEquals(pessoaPage, result);
    }

    @DisplayName("Teste_para_buscar_uma_Pessoa_por_id")
    @Test
    public void testarBuscarPessoaPorId() throws RegraDeNegocioException {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.buscarPessoaPorId(1);

        assertEquals(pessoa, result);
    }


    @DisplayName("Teste_para_inativar_uma_Pessoa")
    @Test
    public void testarInativarPessoa() throws RegraDeNegocioException {
        Integer idPessoa = 1;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(idPessoa);
        pessoa.setAtivo("1");

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoa));

        pessoaService.inativarPessoa(idPessoa);

        assertEquals(String.valueOf(Ativo.INATIVO.getIndex()), pessoa.getAtivo());
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @DisplayName("Teste_para_ativar_uma_Pessoa")
    @Test
    public void testarAtivarPessoa() throws RegraDeNegocioException {
        Integer idPessoa = 1;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(idPessoa);
        pessoa.setAtivo("0");

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoa));

        pessoaService.ativarPessoa(idPessoa);

        assertEquals(String.valueOf(Ativo.ATIVO.getIndex()), pessoa.getAtivo());
        verify(pessoaRepository).save(any(Pessoa.class));

    }

    @DisplayName("Teste_para_buscar_uma_Pessoa_por_CPF")
    @Test
    public void testarBuscarPessoaPorCpf() throws RegraDeNegocioException {
        String cpf = "12345678901";
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);

        when(pessoaRepository.findByCpf(cpf)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.buscarPessoaPorCpf(cpf);

        assertEquals(pessoa, result);
    }

    @Test
    public void testValidarPessoa() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setCpf("12345678901");

        PessoaService pessoaService = new PessoaService(pessoaRepository, objectMapper);

        Method method = PessoaService.class.getDeclaredMethod("validarPessoa", PessoaCreateDTO.class);

        method.setAccessible(true);

        method.invoke(pessoaService, pessoaCreateDTO);
    }

    @DisplayName("Teste para validar um CPF inválido")
    @Test
    public void testarValidarCpfInvalido() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setCpf("1234567890");

        Method method = PessoaService.class.getDeclaredMethod("validarPessoa", PessoaCreateDTO.class);
        method.setAccessible(true);

        InvocationTargetException invocationTargetException = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(pessoaService, pessoaCreateDTO);
        });

        assertTrue(invocationTargetException.getCause() instanceof RegraDeNegocioException);
        assertEquals("CPF inválido", invocationTargetException.getCause().getMessage());
    }

}