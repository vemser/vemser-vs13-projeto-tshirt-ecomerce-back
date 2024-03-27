package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Endereco;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.EnderecoRepository;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockEndereco;
import br.com.dbc.vemser.iShirts.service.mocks.MockPessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EnderecoService - Test")
class EnderecoServiceTest {
    @Mock
    private PessoaService pessoaService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaRepository pessoaRepository;
    @InjectMocks
    private EnderecoService enderecoService;
    

    @DisplayName("Deveria listar todos os endereços")
    @Test
    void deveriaListarTodoEndereco(){
        Page<Endereco> pageEnitty = new PageImpl<>(List.of(MockEndereco.retornaEntity()));
        Page<EnderecoDTO> pageDTO = new PageImpl<>(List.of(MockEndereco.retornaDTO()));
        Endereco enderecoEntity = MockEndereco.retornaEntity();
        EnderecoDTO enderecoDTO = MockEndereco.retornaDTO();
        Pageable pageable = PageRequest.of(1, 1);

        when(enderecoRepository.findAll(pageable)).thenReturn(pageEnitty);
        when(objectMapper.convertValue(any(), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);

        Page<EnderecoDTO> pageCurrent = enderecoService.listarTodos(1, 1);
        assertNotNull(pageCurrent);
        assertEquals(pageCurrent.get().toList().get(0).getCep(), "00000-000");
    }

    @DisplayName("Deveria listar endereço por pessoa")
    @Test
    void deveriaListarTodoEnderecoPessoaId(){
        Page<Endereco> pageEnitty = new PageImpl<>(List.of(MockEndereco.retornaEntity()));
        EnderecoDTO enderecoDTO = MockEndereco.retornaDTO();

        when(enderecoRepository.findAllByIdPessoaIs(any(), any())).thenReturn(pageEnitty);
        when(objectMapper.convertValue(any(), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);

        Page<EnderecoDTO> pageCurrent = enderecoService.listarPorPessoa(1, 1, 1);
        assertNotNull(pageCurrent);
        assertEquals(pageCurrent.get().toList().get(0).getComplemento(), "Complemento teste");
    }

    @DisplayName("Deveria salvar um endereço")
    @Test
    void deveriaSalvarEndereco() throws RegraDeNegocioException {
        EnderecoCreateDTO enderecoCreateDTO = MockEndereco.retornaCreate();
        Endereco endereco = MockEndereco.retornaEntity();
        EnderecoDTO enderecoDTO = MockEndereco.retornaDTO();
        Pessoa pessoa = MockPessoa.retornarEntity();

        when(objectMapper.convertValue(any(EnderecoCreateDTO.class), eq(Endereco.class))).thenReturn(endereco);
        when(objectMapper.convertValue(any(Endereco.class), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);
        when(pessoaService.buscarPessoaPorId(any())).thenReturn(pessoa);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoDTO enderecoCurrent = enderecoService.salvarEndereco(enderecoCreateDTO);
        assertNotNull(enderecoCurrent);
        assertEquals(enderecoCurrent.getBairro(), enderecoDTO.getBairro());
    }

    @DisplayName("Deveria retorna entidade endereço por id")
    @Test
    void deveriaRetornaEnderecoId() throws RegraDeNegocioException {
        Endereco endereco = MockEndereco.retornaEntity();

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(endereco));

        Endereco enderecoCurrent = enderecoService.buscarPorId(new Random().nextInt());
        assertNotNull(enderecoCurrent);
        assertEquals(enderecoCurrent.getIdEndereco(), endereco.getIdEndereco());
    }

    @DisplayName("Deveria lançar uma exceção ao procurar endereço por id")
    @Test
    void deveriaLançaExcecaoProcuraEnderecoId() throws RegraDeNegocioException {
        assertThrows(RegraDeNegocioException.class, () -> {enderecoService.buscarPorId(new Random().nextInt());});
    }

    @DisplayName("Deveria retorna entidadeDTO por id")
    @Test
    void deveriaRetornaEntidadeDTOId() throws RegraDeNegocioException{
        Endereco endereco = MockEndereco.retornaEntity();
        EnderecoDTO enderecoDTO = MockEndereco.retornaDTO();

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(endereco));
        when(objectMapper.convertValue(any(Endereco.class), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);

        EnderecoDTO enderecoCurrent = enderecoService.buscarPorIdDto(new Random().nextInt());
        assertNotNull(enderecoCurrent);
        assertEquals(enderecoCurrent.getIdEndereco(), enderecoDTO.getIdEndereco());
    }

    @DisplayName("Deveria lançar uma exceção ao procurar endereçoDTO por id")
    @Test
    void deveriaLançaExcecaoProcuraEnderecoDTOId(){
        assertThrows(RegraDeNegocioException.class, () -> {enderecoService.buscarPorIdDto(new Random().nextInt());});
    }

    @DisplayName("Deveria editar um endereço")
    @Test
    void deveriaEditarEndereco() throws RegraDeNegocioException {
        Endereco endereco = MockEndereco.retornaEntity();
        EnderecoDTO enderecoDTO = MockEndereco.retornaDTO();
        EnderecoCreateDTO enderecoCreateDTO = MockEndereco.retornaCreate();

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(endereco));
        when(objectMapper.convertValue(any(Endereco.class), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);

        EnderecoDTO enderecoDTOCurrent = enderecoService.editarEndereco(enderecoCreateDTO, new Random().nextInt());
        assertNotNull(enderecoDTOCurrent);
        assertEquals(enderecoDTO.getCidade(), enderecoDTOCurrent.getCidade());
    }

    @DisplayName("Deveria deletar um endereço")
    @Test
    void deveriaDeletarEndereco() throws RegraDeNegocioException {
        Endereco endereco = MockEndereco.retornaEntity();

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(endereco));

        enderecoService.deletar(new Random().nextInt());

        verify(enderecoRepository, times(1)).delete(endereco);
    }
}