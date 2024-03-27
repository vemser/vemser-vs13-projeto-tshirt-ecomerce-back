package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.auth.*;
import br.com.dbc.vemser.iShirts.dto.usuario.*;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Login", description = "Controller responsável pelas operações relacionadas à Autenticação.")
public interface AuthControllerInterface {

    @Operation(summary = "Autentica um usuário.", description = "Autentica um usuário e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })

    @PostMapping("/login")
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException;

    @Operation(summary = "Cria um novo Cliente - usado por qualquer um para Criar uma Conta como Cliente", description = "Cria um novo cliente com o cargo CLIENTE e retorna os detalhes do cliente criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PostMapping("/criar-cliente")
    public ResponseEntity<UsuarioDTO> criarCliente(@Valid @RequestBody ClienteCreateDTO usuario) throws RegraDeNegocioException;

    @Operation(summary = "Retorna o usuário logado.", description = "Retorna o nome do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping("/usuario-logado")
    public UsuarioLoginDTO usuarioLogado() throws RegraDeNegocioException;
}