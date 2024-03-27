package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.UsuarioControllerInterface;
import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/usuario")
public class UsuarioController implements UsuarioControllerInterface {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar-inativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosInativos() throws RegraDeNegocioException {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuariosInativos();
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/listar-ativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAtivos() throws RegraDeNegocioException {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuariosAtivos();
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/buscar-por-id/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) throws RegraDeNegocioException {
       UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/criar-usuario")
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        UsuarioDTO novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody @Valid UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException {
        UsuarioDTO usuarioAtualizadoSalvo = usuarioService.atualizarUsuario(usuarioService.getIdLoggedUser(), usuarioAtualizado);
        return ResponseEntity.ok(usuarioAtualizadoSalvo);
    }

    @DeleteMapping
    public ResponseEntity<String> InativarUsuario() throws RegraDeNegocioException {
        usuarioService.inativarUsuario(usuarioService.getIdLoggedUser());
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<UsuarioDTO> alterarSenha(@RequestBody @Valid AlteraSenhaDTO alteraSenhaDTO) throws Exception {
        return ResponseEntity.ok(usuarioService.alterarSenha(alteraSenhaDTO));
    }

}
