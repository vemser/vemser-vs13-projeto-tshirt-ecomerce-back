package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.ClienteCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.iShirts.model.Cargo;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MockUsuario {
    public static Usuario retornarEntity(){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(new Random().nextInt());
        usuario.setAtivo(Ativo.ATIVO);
        usuario.setEmail("teste@dbccompany.com.br");
        usuario.setSenha("senha123");
        return usuario;
    }
    public static List<Usuario> retornarListaUsuarioEntity(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(retornarEntity());
        usuarios.add(retornarEntity());
        return usuarios;
    }
    public static List<UsuarioDTO> retornarListaUsuarioDTO(){
        List<UsuarioDTO> usuarios = new ArrayList<>();
        usuarios.add(retornarUsuarioDTO());
        usuarios.add(retornarUsuarioDTO());
        return usuarios;
    }
    public static UsuarioDTO retornarDTOPorEntity(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getIdUsuario());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
    public static UsuarioDTO retornarUsuarioDTO(){
        return retornarDTOPorEntity(retornarEntity());
    }
    public static UsuarioCreateDTO retornarUsuarioCreateDTO(){
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setEmail("teste@dbccompany.com.br");
        usuarioCreateDTO.setSenha("senha123");
        usuarioCreateDTO.setCargos(Set.of(retornaCargo()));
        return usuarioCreateDTO;
    }

    public static ClienteCreateDTO retornarClienteCreateDTO(){
        ClienteCreateDTO usuarioCreateDTO = new ClienteCreateDTO();
        usuarioCreateDTO.setEmail("teste@dbccompany.com.br");
        usuarioCreateDTO.setSenha("senha123");

        return usuarioCreateDTO;
    }

    public static UsuarioUpdateDTO retornarUsuarioUpdate(){
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        usuarioUpdateDTO.setEmail("testeatualiza@dbccompany.com.br");
        return usuarioUpdateDTO;
    }

    public static Cargo retornaCargo(){
        Cargo cargo = new Cargo();
        cargo.setIdCargo(1);
        cargo.setDescricao("Admin");

        return cargo;
    }

    public static AlteraSenhaDTO retornaSenhaDTO(){
        AlteraSenhaDTO alterar = new AlteraSenhaDTO();
        alterar.setEmail("email@email.com");
        alterar.setSenhaNova("senha123");
        alterar.setSenhaAtual("senha123");

        return alterar;
    }
}
