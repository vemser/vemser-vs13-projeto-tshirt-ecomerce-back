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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarUsuariosInativos() throws RegraDeNegocioException {
        List<Usuario> usuariosInativos = usuarioRepository.findByAtivo(Ativo.INATIVO);
        if (usuariosInativos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum usuário Inativo encontrado");
        }
        return usuariosInativos.stream()
                .map(this::convertToUsuarioDTOWithId)
                .collect(Collectors.toList());
    }
    public List<UsuarioDTO> listarUsuarios() throws RegraDeNegocioException {
        List<Usuario> usuarios = usuarioRepository.findAllByAtivo(Ativo.ATIVO);
        if (usuarios.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum usuário encontrado");
        }
        return usuarios.stream()
                .map(this::convertToUsuarioDTOWithId)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarUsuarioPorId(Integer id) throws RegraDeNegocioException {
        Optional<Usuario> usuario = usuarioRepository.findByIdUsuarioAndAtivo(id, Ativo.ATIVO);
        if (usuario.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        return usuario.map(this::convertToUsuarioDTOWithId);
    }

    public UsuarioDTO criarUsuario(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new RegraDeNegocioException("E-mail e senha são obrigatórios");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RegraDeNegocioException("E-mail Inválido");
        }

        Usuario novoUsuario = CreatetoUsuario(usuario);
        novoUsuario.setAtivo(Ativo.ATIVO);
        usuarioRepository.save(novoUsuario);
        return convertToUsuarioDTOWithId(novoUsuario);
    }

    public UsuarioDTO atualizarUsuario(Integer id, UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdUsuarioAndAtivo(id, Ativo.ATIVO);
        if (usuarioOptional.isPresent()) {
            if (!usuarioOptional.get().getEmail().equals(usuarioAtualizado.getEmail())
                    && usuarioRepository.existsByEmailAndAtivo(usuarioAtualizado.getEmail(), Ativo.ATIVO)) {
                throw new RegraDeNegocioException("E-mail Inválido");
            }

            Usuario usuarioAtualizadoEntidade = UpdatetoUsuario(usuarioAtualizado);
            usuarioAtualizadoEntidade.setIdUsuario(usuarioOptional.get().getIdUsuario());
            usuarioAtualizadoEntidade.setAtivo(Ativo.ATIVO);
            usuarioRepository.save(usuarioAtualizadoEntidade);
            return convertToUsuarioDTOWithId(usuarioAtualizadoEntidade);
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
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
                throw new RegraDeNegocioException("Usuário já está inativo");
            }
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
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
        return convertToUsuarioDTOWithId(usuarioRepository.save(usuarioAtualizar.get()));
    }

    private UsuarioDTO convertToUsuarioDTOWithId(Usuario entity) {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(entity, UsuarioDTO.class);
        usuarioDTO.setId(entity.getIdUsuario());
        return usuarioDTO;
    }
    public Usuario UpdatetoUsuario(UsuarioUpdateDTO entity) {
        return objectMapper.convertValue(entity, Usuario.class);
    }
    public Usuario CreatetoUsuario(UsuarioCreateDTO entity) {
        return objectMapper.convertValue(entity, Usuario.class);
    }
}
