package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.iShirts.dto.usuario.ClienteCreateDTO;
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

    @Operation(summary = "Listar todos os usuários ativos - usado pelo ADMIN", description = "API para listar todos os usuários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<List<UsuarioDTO>> listarUsuariosAtivos() throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os usuários inativos - usado pelo ADMIN", description = "API para listar todos os usuários inativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários inativos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<List<UsuarioDTO>> listarUsuariosInativos() throws RegraDeNegocioException;

    @Operation(summary = "Buscar usuário por ID - usado pelo ADMIN", description = "API para buscar um usuário pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID do usuário não é válido."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Cria um novo usuário setando os cargos - usado por ADMIN", description = "Cria um novo usuário onde você pode setar quantos cargos quiser, e retorna os detalhes do usuário criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PostMapping("/criar-usuario")
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar usuário - usado pelo CLIENTE/FUNCIONARIO/ADMIN", description = "API para atualizar um usuário existente (apenas o atributo email - para o resto chame o atualizar pessoa).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados para atualização do usuário não são válidos."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody @Valid UsuarioUpdateDTO usuarioAtualizado) throws RegraDeNegocioException;

    @Operation(summary = "Inativa o usuário - usado pelo CLIENTE", description = "API para deletar um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID do usuário não é válido."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    public ResponseEntity<String> InativarUsuario() throws RegraDeNegocioException;

    @Operation(summary = "Altera a senha de um usuário.", description = "Altera a senha de um usuário e retorna os detalhes do usuário atualizado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PutMapping("/alterar-senha")
    public ResponseEntity<UsuarioDTO> alterarSenha(@RequestBody @Valid AlteraSenhaDTO alteraSenhaDTO) throws Exception;
}
