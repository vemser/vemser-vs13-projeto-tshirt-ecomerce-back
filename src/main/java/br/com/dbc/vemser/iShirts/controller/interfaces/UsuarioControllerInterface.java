package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "Usuário", description = "Controller responsável pelas operações relacionadas à usuário.")
public interface UsuarioControllerInterface {

    @Operation(summary = "Listar todos os usuários ativos", description = "API para listar todos os usuários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<List<UsuarioDTO>> listarUsuarios() throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os usuários inativos", description = "API para listar todos os usuários inativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários inativos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<List<UsuarioDTO>> listarUsuariosInativos() throws RegraDeNegocioException;

    @Operation(summary = "Buscar usuário por ID", description = "API para buscar um usuário pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID do usuário não é válido."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Criar novo usuário", description = "API para criar um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados para criação do usuário não são válidos."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar usuário", description = "API para atualizar um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados para atualização do usuário não são válidos."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException;

    @Operation(summary = "Deletar usuário", description = "API para deletar um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID do usuário não é válido."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<String> deletarUsuario(@PathVariable Integer id) throws RegraDeNegocioException;
}
