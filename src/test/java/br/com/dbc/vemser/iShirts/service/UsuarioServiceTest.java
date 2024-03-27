package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.ClienteCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import br.com.dbc.vemser.iShirts.repository.UsuarioRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - Test")
class UsuarioServiceTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CargoService cargoService;
    @Spy
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deveria listar usuários inativos")
    void listarUsuariosInativos() throws RegraDeNegocioException {
        List<Usuario> usuarios = MockUsuario.retornarListaUsuarioEntity();

        when(usuarioRepository.findByAtivo(Ativo.INATIVO)).thenReturn(usuarios);
        when(objectMapper.convertValue(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(MockUsuario.retornarUsuarioDTO());

        List<UsuarioDTO> usuariosResponse = usuarioService.listarUsuariosInativos();

        Assertions.assertAll(
                () -> assertNotNull(usuariosResponse),
                () -> assertEquals(2, usuariosResponse.size())
        );
    }
    @Test
    @DisplayName("Não deve listar usuarios inativos quando nao há")
    void naoDevelistarUsuariosInativos() throws RegraDeNegocioException {

        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.listarUsuariosInativos();
        }, "Nenhum usuário Inativo encontrado");
    }

    @Test
    @DisplayName("Deveria listar usuários ativos")
    void listarUsuarios() throws RegraDeNegocioException {
        List<Usuario> usuarios = MockUsuario.retornarListaUsuarioEntity();

        when(usuarioRepository.findByAtivo(Ativo.ATIVO)).thenReturn(usuarios);
        when(objectMapper.convertValue(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(MockUsuario.retornarUsuarioDTO());

        List<UsuarioDTO> usuariosResponse = usuarioService.listarUsuariosAtivos();

        Assertions.assertAll(
                () -> assertNotNull(usuariosResponse),
                () -> assertEquals(2, usuariosResponse.size())
        );
    }
    @Test
    @DisplayName("Não deve listar usuarios ativos quando nao há")
    void naoDevelistarUsuarioAtivos() throws RegraDeNegocioException {
        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.listarUsuariosAtivos();
        }, "Nenhum usuário encontrado");
    }

    @Test
    @DisplayName("Deveria buscar um usuário pelo id")
    void buscarUsuarioPorId() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioDTO usuarioDTO = MockUsuario.retornarDTOPorEntity(usuario);

        when(usuarioRepository.findByIdUsuarioAndAtivo(usuario.getIdUsuario(), Ativo.ATIVO)).thenReturn(Optional.of(usuario));
        when(objectMapper.convertValue(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        UsuarioDTO usuarioDTOResponse = usuarioService.buscarUsuarioPorId(usuario.getIdUsuario());

        Assertions.assertAll(
                () -> assertNotNull(usuarioDTOResponse),
                () -> assertEquals(usuarioDTOResponse, usuarioDTO)
        );

    }

    @Test
    @DisplayName("Não deve listar usuarios ativos quando nao há")
    void naoDeveBuscarUsuarioInexistente() throws RegraDeNegocioException {
        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.buscarUsuarioPorId(2);
        }, "Usuário não encontrado");
    }

    @Test
    @DisplayName("Deveria criar um usuário com sucesso")
    void criarUsuario() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioDTO usuarioDTO = MockUsuario.retornarDTOPorEntity(usuario);
        UsuarioCreateDTO usuarioCreateDTO = MockUsuario.retornarUsuarioCreateDTO();

        when(objectMapper.convertValue(usuarioCreateDTO, Usuario.class)).thenReturn(usuario);
        when(objectMapper.convertValue(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);
        when(passwordEncoder.encode(anyString())).thenReturn("SenhaCriptografada");
        when(cargoService.buscarCargoPorId(any())).thenReturn(MockUsuario.retornaCargo());

        UsuarioDTO usuarioDTOResponse = usuarioService.criarUsuario(usuarioCreateDTO);

        Assertions.assertAll(
                () -> assertNotNull(usuarioDTOResponse),
                () -> assertEquals(usuarioDTOResponse, usuarioDTO)
        );
    }


    @Test
    @DisplayName("Não deve criar usuario com email ou senha inválidos")
    void naoDevCriarUsuarioemailSenhaInv() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();

        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.criarUsuario(usuarioCreateDTO);
        }, "E-mail e senha são obrigatórios");
    }

    @Test
    @DisplayName("Não deve criar usuario com email já cadastrado")
    void naoDevCriarUsuarioEmailCadastrado() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = MockUsuario.retornarUsuarioCreateDTO();

        when(usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())).thenReturn(true);
        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.criarUsuario(usuarioCreateDTO);
        }, "E-mail Inválido");
    }


    @Test
    @DisplayName("Deveria atualizar um usuário com sucesso")
    void atualizarUsuario() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioDTO usuarioDTO = MockUsuario.retornarDTOPorEntity(usuario);
        UsuarioUpdateDTO usuarioUpdateDTO = MockUsuario.retornarUsuarioUpdate();
        usuarioDTO.setEmail(usuarioUpdateDTO.getEmail());

        when(usuarioRepository.findByIdUsuarioAndAtivo(usuario.getIdUsuario(), Ativo.ATIVO)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndAtivo(usuarioUpdateDTO.getEmail(), Ativo.ATIVO)).thenReturn(false);
        when(objectMapper.convertValue(usuarioUpdateDTO, Usuario.class)).thenReturn(usuario);
        when(objectMapper.convertValue(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        UsuarioDTO usuarioDTOResponse = usuarioService.atualizarUsuario(usuario.getIdUsuario(), usuarioUpdateDTO);

        Assertions.assertAll(
                () -> assertNotNull(usuarioDTOResponse),
                () -> assertEquals(usuarioDTOResponse.getEmail(), usuarioUpdateDTO.getEmail())
        );

    }

    @Test
    @DisplayName("Não deve atualizar usuario com email diferente já cadastrado")
    void naoDevAtualizarUsuarioEmailCadastrado() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioUpdateDTO usuarioUpdateDTO = MockUsuario.retornarUsuarioUpdate();

        when(usuarioRepository.findByIdUsuarioAndAtivo(usuario.getIdUsuario(), Ativo.ATIVO)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndAtivo(usuarioUpdateDTO.getEmail(), Ativo.ATIVO)).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.atualizarUsuario(usuario.getIdUsuario(), usuarioUpdateDTO);
        }, "E-mail Inválido");
    }

    @Test
    @DisplayName("Não deve atualizar usuario que nao existe")
    void naoDevAtualizarUsuarioNaoExiste() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioUpdateDTO usuarioUpdateDTO = MockUsuario.retornarUsuarioUpdate();

        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.atualizarUsuario(usuario.getIdUsuario(), usuarioUpdateDTO);
        }, "Usuário não encontrado");
    }


    @Test
    @DisplayName("Deveria desativar um usuário com sucesso")
    void deletarUsuario() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();

        when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));

        usuarioService.inativarUsuario(usuario.getIdUsuario());

        assertEquals(usuario.getAtivo(), Ativo.INATIVO);
    }
    @Test
    @DisplayName("Não deve desativar usuario ja invativo")
    void naoDevDesativarUsuarioJaInativo() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        usuario.setAtivo(Ativo.INATIVO);
        when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));


        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.inativarUsuario(usuario.getIdUsuario());
        }, "Usuário já está inativo");
    }

    @Test
    @DisplayName("Não deve desativar usuario que nao existe")
    void naoDevDesativarUsuarioNaoExiste() throws RegraDeNegocioException {
        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.inativarUsuario(1);
        }, "Usuário não encontrado");
    }


    @Test
    @DisplayName("Deve criar cliente com sucesso")
    public void criaClienteComSucesso() throws RegraDeNegocioException {
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioDTO usuarioDTO = MockUsuario.retornarDTOPorEntity(usuario);
        ClienteCreateDTO usuarioCreateDTO = MockUsuario.retornarClienteCreateDTO();

        when(objectMapper.convertValue(usuarioCreateDTO, Usuario.class)).thenReturn(usuario);
        when(objectMapper.convertValue(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);
        when(passwordEncoder.encode(anyString())).thenReturn("SenhaCriptografada");

        UsuarioDTO usuarioDTOResponse = usuarioService.criarCliente(usuarioCreateDTO);

        Assertions.assertAll(
                () -> assertNotNull(usuarioDTOResponse),
                () -> assertEquals(usuarioDTOResponse, usuarioDTO)
        );
    }

    @Test
    @DisplayName("Deve alterar a senha com sucesso")
    public void alteraSenhaComSucesso() throws Exception {
        AlteraSenhaDTO alterar = MockUsuario.retornaSenhaDTO();
        Usuario usuario = MockUsuario.retornarEntity();
        UsuarioDTO usuarioDTO = MockUsuario.retornarDTOPorEntity(usuario);

        doReturn(Optional.of(usuario)).when(usuarioService).buscarUsuarioPorEmail(anyString());
        when(passwordEncoder.encode(anyString())).thenReturn("SenhaCriptografada");
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(objectMapper.convertValue(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTO);

        UsuarioDTO usuarioDTOResponse = usuarioService.alterarSenha(alterar);

        assertNotNull(usuarioDTOResponse);
        assertEquals(usuarioDTOResponse, usuarioDTO);
    }

    @Test
    @DisplayName("Usuario não existe no banco de dados")
    public void retornaExceptionSemEntidade(){
        AlteraSenhaDTO alterar = MockUsuario.retornaSenhaDTO();
        alterar.setEmail("111");

        assertThrows(RegraDeNegocioException.class, () -> usuarioService.alterarSenha(alterar));
    }

    @Test
    @DisplayName("Usuario passa a senha atual errada")
    public void retornaExceptionDadosDiferentes(){
        Usuario usuario = MockUsuario.retornarEntity();
        AlteraSenhaDTO alterar = MockUsuario.retornaSenhaDTO();
        alterar.setSenhaAtual("SenhaErrada");

        doReturn(Optional.of(usuario)).when(usuarioService).buscarUsuarioPorEmail(anyString());


        assertThrows(RegraDeNegocioException.class, () -> usuarioService.alterarSenha(alterar));
    }

    @Test
    void testBuscarIdUsuarioLogado() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(0, "password");
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Integer idUsuario = usuarioService.buscarIdUsuarioLogado();

        assertNotNull(idUsuario);
        assertEquals(0, idUsuario);
    }

    //TODO ARRUMAR O TESTE
    @Test
    void testBuscarUsuarioLogado() throws RegraDeNegocioException {
//        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
//        SecurityContext securityContext = mock(SecurityContext.class);
//
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        Usuario usuarioLogado = usuarioService.buscarUsuarioLogado(2);
//
//        assertNotNull(usuarioLogado);
//        assertEquals("username", usuarioLogado);
    }
}

