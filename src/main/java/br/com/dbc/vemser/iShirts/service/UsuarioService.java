package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.*;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Cargo;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import br.com.dbc.vemser.iShirts.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CargoService cargoService;

    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private static final String MSG_USUARIO_NAO_ENCONTRADO_INATIVO = "Usuário não encontrado ou já está inativo";
    private static final String MSG_NENHUM_USUARIO_ENCONTRADO = "Nenhum Usuário encontrado";
    private static final String MSG_EMAIL_SENHA_OBRIGATORIOS = "E-mail e senha são obrigatórios";
    private static final String MSG_EMAIL_INVALIDO = "E-mail Inválido";



    public List<UsuarioDTO> listarUsuariosInativos() throws RegraDeNegocioException {
        List<Usuario> usuariosInativos = usuarioRepository.findByAtivo(Ativo.INATIVO);
        verificarListaUsuariosVazia(usuariosInativos);
        return usuariosInativos.stream()
                .map(this::converterParaUsuarioDTOComId)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarUsuariosAtivos() throws RegraDeNegocioException {
        List<Usuario> usuarios = usuarioRepository.findByAtivo(Ativo.ATIVO);
        verificarListaUsuariosVazia(usuarios);
        return usuarios.stream()
                .map(this::converterParaUsuarioDTOComId)
                .collect(Collectors.toList());
    }

    private void verificarListaUsuariosVazia(List<Usuario> usuarios) throws RegraDeNegocioException {
        if (usuarios.isEmpty()) {
            throw new RegraDeNegocioException(UsuarioService.MSG_NENHUM_USUARIO_ENCONTRADO);
        }
    }

    public UsuarioDTO buscarUsuarioPorId(Integer id) throws RegraDeNegocioException {
        Optional<Usuario> usuarioOptional = getUsuarioAtivoById(id);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO));
        return converterParaUsuarioDTOComId(usuario);
    }

    public UsuarioDTO atualizarUsuario(Integer id, UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException {
        Usuario usuarioAtualizadoEntidade = getUsuarioAtivoById(id)
                .orElseThrow(() -> new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO));

        if (usuarioRepository.existsByEmailAndAtivo(usuarioAtualizado.getEmail(), Ativo.ATIVO)) {
            throw new RegraDeNegocioException(MSG_EMAIL_INVALIDO);
        }

        usuarioAtualizadoEntidade = updateParaUsuario(usuarioAtualizado, usuarioAtualizadoEntidade);
        usuarioAtualizadoEntidade.setAtivo(Ativo.ATIVO);
        usuarioRepository.save(usuarioAtualizadoEntidade);
        return converterParaUsuarioDTOComId(usuarioAtualizadoEntidade);
    }

    private Optional<Usuario> getUsuarioAtivoById(Integer id) throws RegraDeNegocioException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdUsuarioAndAtivo(id, Ativo.ATIVO);
        if (usuarioOptional.isEmpty()) {
            throw new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO);
        }
        return usuarioOptional;
    }

    public UsuarioDTO criarUsuario(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        validarDadosUsuarioCreate(usuario);
        verificarExistenciaEmail(usuario.getEmail());

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());

        Set<Cargo> cargos = usuario.getCargos();
        if (cargos == null || cargos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum cargo especificado para o novo usuário");
        }

        for (Cargo cargo : cargos) {
            Integer cargoId = cargo.getIdCargo();
            if (cargoId == null) {
                throw new RegraDeNegocioException("ID do cargo não especificado");
            }

            Cargo cargoExistente = cargoService.buscarCargoPorId(cargoId);
            if (cargoExistente == null) {
                throw new RegraDeNegocioException("Cargo com ID " + cargoId + " não encontrado");
            }
        }

        Usuario novoUsuario = createParaUsuario(usuario);
        novoUsuario.setAtivo(Ativo.ATIVO);
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setCargos(new HashSet<>(cargos));

        usuarioRepository.save(novoUsuario);
        return converterParaUsuarioDTOComId(novoUsuario);
    }
    public UsuarioDTO criarCliente(ClienteCreateDTO usuario) throws RegraDeNegocioException {
        validarDadosClienteCreate(usuario);
        verificarExistenciaEmail(usuario.getEmail());

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        Cargo cargoCliente = cargoService.buscarCargoPorDescricao("ROLE_CLIENTE");

        Usuario novoUsuario = createParaCliente(usuario);
        novoUsuario.setAtivo(Ativo.ATIVO);
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setCargos(new HashSet<>());
        novoUsuario.getCargos().add(cargoCliente);

        usuarioRepository.save(novoUsuario);
        return converterParaUsuarioDTOComId(novoUsuario);
    }

    private void validarDadosUsuarioCreate(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new RegraDeNegocioException(MSG_EMAIL_SENHA_OBRIGATORIOS);
        }
    }
    private void validarDadosClienteCreate(ClienteCreateDTO usuario) throws RegraDeNegocioException {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new RegraDeNegocioException(MSG_EMAIL_SENHA_OBRIGATORIOS);
        }
    }
    private void verificarExistenciaEmail(String email) throws RegraDeNegocioException {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraDeNegocioException(MSG_EMAIL_INVALIDO);
        }
    }

    public void inativarUsuario(Integer id) throws RegraDeNegocioException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getAtivo() != Ativo.INATIVO) {
                usuario.setAtivo(Ativo.INATIVO);
                Pessoa pessoa = usuario.getPessoa();
                pessoa.setAtivo(String.valueOf(Ativo.INATIVO.getIndex()));
                usuarioRepository.save(usuario);
            } else {
                throw new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO_INATIVO);
            }
        } else {
            throw new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO);
        }
    }

    public UsuarioDTO alterarSenha(AlteraSenhaDTO alteraSenhaDTO) throws Exception {
        Optional<Usuario> usuarioAtualizar = buscarUsuarioPorEmail(alteraSenhaDTO.getEmail());
        if (usuarioAtualizar.isEmpty()) {
            throw new RegraDeNegocioException("Usuário ou Senha inválida");
        }
        if (!passwordEncoder.matches(alteraSenhaDTO.getSenhaAtual(), usuarioAtualizar.get().getSenha())) {
            throw new RegraDeNegocioException("Usuário ou Senha inválida");
        }
        String senhaNovaCriptografada = passwordEncoder.encode(alteraSenhaDTO.getSenhaNova());
        usuarioAtualizar.get().setSenha(senhaNovaCriptografada);
        return converterParaUsuarioDTOComId(usuarioRepository.save(usuarioAtualizar.get()));
    }

    private UsuarioDTO converterParaUsuarioDTOComId(Usuario entity) {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(entity, UsuarioDTO.class);
        usuarioDTO.setId(entity.getIdUsuario());
        usuarioDTO.setCargos(entity.getCargos());
        return usuarioDTO;
    }

    private Usuario updateParaUsuario(UsuarioUpdateDTO entity, Usuario usuario) {
        Usuario usuarioAtualizado = objectMapper.convertValue(entity, Usuario.class);
        usuarioAtualizado.setIdUsuario(usuario.getIdUsuario());
        return usuarioAtualizado;
    }

    private Usuario createParaUsuario(UsuarioCreateDTO entity) {
        return objectMapper.convertValue(entity, Usuario.class);
    }

    private Usuario createParaCliente(ClienteCreateDTO entity) {
        return objectMapper.convertValue(entity, Usuario.class);
    }

    public Usuario buscarUsuarioLogadoEntity() throws RegraDeNegocioException {
        Integer id = buscarIdUsuarioLogado();
        return usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado com o ID: " + id));
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Integer buscarIdUsuarioLogado() {
        return Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public Usuario buscarUsuarioPorIdWithCargo(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findByIdWithCargos(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado com o ID: " + id));
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public UsuarioLoginDTO getUsuarioLogado(Integer id) throws RegraDeNegocioException {
        Usuario usuario = buscarUsuarioPorIdWithCargo(id);
        UsuarioLoginDTO usuarioLoginDTO = objectMapper.convertValue(usuario, UsuarioLoginDTO.class);
        usuarioLoginDTO.setCargos(usuario.getCargos());
        return usuarioLoginDTO;
    }
}
