package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.cupom.CupomCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Cupom", description = "Controller responsável pelas operações relacionadas à cupom.")
public interface CupomControllerInterface {
    @Operation(summary = "Listagem de todos os cupons", description = "Listagem de todos as cupons cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de cupons realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Page<CupomDTO>> listar(@PageableDefault(value = 10, page = 0, sort = "validade") Pageable pageable);

    @Operation(summary = "Buscar cupom por id", description = "Buscar cupom pelo id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<CupomDTO> buscarPorId(@PathVariable Integer idCupom) throws RegraDeNegocioException;

    @Operation(summary = "Cadastrar cupom", description = "Cadastrar um novo cupom no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de cupom realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<CupomDTO> criar(@Valid @RequestBody CupomCreateDTO cupomDto);

    @Operation(summary = "Editar cupom", description = "Editar os dados de um cupom no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom editado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<CupomDTO> editar(@PathVariable Integer idCupom, @Valid @RequestBody CupomUpdateDTO cupomDto) throws RegraDeNegocioException;

    @Operation(summary = "Remover cupom", description = "Remover um cupom do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cupom removido com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    ResponseEntity<Void> deletar(@PathVariable Integer idCupom) throws RegraDeNegocioException;
}
