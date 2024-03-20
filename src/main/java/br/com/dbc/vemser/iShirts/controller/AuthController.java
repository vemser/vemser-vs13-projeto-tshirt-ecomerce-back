package br.com.dbc.vemser.iShirts.controller;


import br.com.dbc.vemser.iShirts.dto.auth.*;
import br.com.dbc.vemser.iShirts.dto.usuario.*;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.security.TokenService;
import br.com.dbc.vemser.iShirts.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    public final AuthenticationManager authenticationManager;
    public final UsuarioService usuarioService;

    @PostMapping("/login")
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        Usuario usuarioValidado = (Usuario) authentication.getPrincipal();

        return tokenService.generateToken(usuarioValidado);
    }

    @PostMapping("/criar-usuario")
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        UsuarioDTO novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
    @PostMapping("/criar-cliente")
    public ResponseEntity<UsuarioDTO> criarCliente(@Valid @RequestBody ClienteCreateDTO usuario) throws RegraDeNegocioException {
        UsuarioDTO novoUsuario = usuarioService.criarCliente(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<UsuarioDTO> alterarSenha(@RequestBody @Valid AlteraSenhaDTO alteraSenhaDTO) throws Exception {
        return ResponseEntity.ok(usuarioService.alterarSenha(alteraSenhaDTO));
    }

    @GetMapping("/usuario-logado")
    public String usuarioLogado() throws Exception{
        return usuarioService.buscarUsuarioLogado();
    }
}
