package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Variação", description = "Controller responsável pelas operações relacionadas à variação.")
public interface VariacaoControllerInterface {

    @Operation(summary = "Criar variação", description = "Cria variações no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de variação realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<VariacaoDTO> criarVariacao(@RequestBody @Valid VariacaoCreateDTO variacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar variação por ID", description = "Lista as variações no sistema por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de variação realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<VariacaoDTO> listarPorID(@PathVariable("id") Integer id) throws Exception;

    @Operation(summary = "Listar variações", description = "Lista todas as variações do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de todas as variações realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Page<VariacaoDTO>> listarVariacoes(@PageableDefault(size = 20, page = 0) Pageable pageable);

    @Operation(summary = "Atualizar variação", description = "Atualiza as variações no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variação atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<VariacaoDTO> editarVariacao(@PathVariable("id") Integer id, @RequestBody VariacaoDTO variacaoDTO) throws Exception;

    @Operation(summary = "Deleta uma variação", description = "Deleta uma variação do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variação deletado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<String> deletarVariacao(@PathVariable("id") Integer id) throws Exception;

    @Operation(summary = "Ativar uma variação por ID", description = "Ativa uma variação por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variação ativada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<String> ativarVariacao(@PathVariable("idProduto") Integer idProduto) throws RegraDeNegocioException;

    @Operation(summary = "Desativa uma variação por ID", description = "Desativa uma variação por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variação desativada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Variação não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<String> desativarVariacao(@PathVariable("idProduto") Integer idProduto) throws RegraDeNegocioException;

}
