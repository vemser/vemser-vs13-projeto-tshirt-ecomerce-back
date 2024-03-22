package br.com.dbc.vemser.iShirts.controller.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Pessoa", description = "Controller responsável pelas operações relacionadas à pessoa.")
public interface PessoaControllerInterface {

    @Operation(summary = "Listagem de todas as pessoas", description = "Listagem de todas as pessoas cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de pessoas realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Page<Pessoa>> buscarTodasPessoas() throws RegraDeNegocioException;

    @Operation(summary = "Buscar pessoa por ID", description = "Buscar pessoa pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException;

    @Operation(summary = "Buscar pessoa por CPF", description = "Buscar pessoa pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Pessoa> buscarPorCpf(@Valid @PathVariable("cpf") String cpf) throws RegraDeNegocioException;

    @Operation(summary = "Cadastrar pessoa", description = "Cadastrar uma nova pessoa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de pessoa realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Not Found."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody PessoaCreateDTO pessoaDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar pessoa", description = "Atualizar os dados de uma pessoa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Pessoa> atualizarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa, @RequestBody PessoaUpdateDTO pessoaDTO) throws RegraDeNegocioException;

    @Operation(summary = "Inativar pessoa", description = "Inativar uma pessoa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa inativada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Void> inativarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException;

    @Operation(summary = "Ativar pessoa", description = "Ativar uma pessoa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa ativada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Void> ativarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException;
}
