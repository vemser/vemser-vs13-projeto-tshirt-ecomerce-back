package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import br.com.dbc.vemser.iShirts.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private static final String MSG_USUARIO_NAO_ENCONTRADO_INATIVO = "Usuário não encontrado ou já está inativo";
    private static final String MSG_EMAIL_SENHA_OBRIGATORIOS = "E-mail e senha são obrigatórios";
    private static final String MSG_EMAIL_INVALIDO = "E-mail Inválido";



    public List<UsuarioDTO> listarUsuariosInativos() throws RegraDeNegocioException {
        List<Usuario> usuariosInativos = usuarioRepository.findByAtivo(Ativo.INATIVO);
        verificarListaUsuariosVazia(usuariosInativos, MSG_USUARIO_NAO_ENCONTRADO_INATIVO);
        return usuariosInativos.stream()
                .map(this::converterParaUsuarioDTOComId)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarUsuarios() throws RegraDeNegocioException {
        List<Usuario> usuarios = usuarioRepository.findAllByAtivo(Ativo.ATIVO);
        verificarListaUsuariosVazia(usuarios, MSG_USUARIO_NAO_ENCONTRADO);
        return usuarios.stream()
                .map(this::converterParaUsuarioDTOComId)
                .collect(Collectors.toList());
    }

    private void verificarListaUsuariosVazia(List<Usuario> usuarios, String mensagemErro) throws RegraDeNegocioException {
        if (usuarios.isEmpty()) {
            throw new RegraDeNegocioException(mensagemErro);
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


        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuarioAtualizadoEntidade = updateParaUsuario(usuarioAtualizado, usuarioAtualizadoEntidade);
        usuarioAtualizadoEntidade.setAtivo(Ativo.ATIVO);
        usuarioAtualizadoEntidade.setSenha(senhaCriptografada);
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

        Usuario novoUsuario = createParaUsuario(usuario);
        novoUsuario.setAtivo(Ativo.ATIVO);
        usuarioRepository.save(novoUsuario);
        return converterParaUsuarioDTOComId(novoUsuario);
    }
    private void validarDadosUsuarioCreate(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new RegraDeNegocioException(MSG_EMAIL_SENHA_OBRIGATORIOS);
        }
    }
    private void verificarExistenciaEmail(String email) throws RegraDeNegocioException {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraDeNegocioException(MSG_EMAIL_INVALIDO);
        }
    }

    public void deletarUsuario(Integer id) throws RegraDeNegocioException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getAtivo() != Ativo.INATIVO) {
                usuario.setAtivo(Ativo.INATIVO);
                usuarioRepository.save(usuario);
            } else {
                throw new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO_INATIVO);
            }
        } else {
            throw new RegraDeNegocioException(MSG_USUARIO_NAO_ENCONTRADO);
        }
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Integer buscarIdUsuarioLogado() {
        return Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public String buscarUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
}
