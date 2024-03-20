package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.UsuarioControllerInterface;
import br.com.dbc.vemser.iShirts.dto.usuario.ClienteCreateDTO;
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
import java.util.Optional;

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
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() throws RegraDeNegocioException {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/buscar-por-id/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) throws RegraDeNegocioException {
       UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    //TODO Utilizar o GetLoggedId user ao inves de receber Id
    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException {
        UsuarioDTO usuarioAtualizadoSalvo = usuarioService.atualizarUsuario(id, usuarioAtualizado);
        return ResponseEntity.ok(usuarioAtualizadoSalvo);
    }

    //TODO Utilizar o GetLoggedId user ao inves de receber Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) throws RegraDeNegocioException {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }
}
