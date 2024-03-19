package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "endereco",description = "Todos os endpoints de endereço")
public interface EnderecoControllerInterface {
    @Operation(summary = "Listagem de todos os endereços", description = "Listagem de todos os endereços cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de todos os endereços  realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarTodos(@RequestParam Integer tamanhoPagina, @RequestParam Integer paginaSolicitada );

    @Operation(summary = "Listagem de todos os endereços por id", description = "Listagem de todos os endereços por id cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de todos os endereços por id realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping("id/{idEndereco}")
    public ResponseEntity<EnderecoDTO> buscarPorIdDto(@PathVariable Integer idEndereco) throws RegraDeNegocioException;

    @Operation(summary = "Listagem de todos os endereços por pessoa", description = "Listagem de todos os endereços por pessoa cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de todos os endereços por pessoa realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping("/por-pessoa/{idPessoa}")
    public ResponseEntity<Page<EnderecoDTO>> listarTodos(@PathVariable Integer idPessoa,@RequestParam Integer tamanhoPagina, @RequestParam Integer paginaSolicitada );


    @Operation(summary = "Criação de um novo endereço por pessoa", description = "Criação de um novo endereço por pessoa cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação de um novo endereço por pessoa realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na Inserir de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody @Valid EnderecoCreateDTO dto) throws RegraDeNegocioException;


    @Operation(summary = "Atualização de um endereço", description = "Atualização de um endereço buscado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização de um endereço buscado por ID realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na atualizar de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> editarEndereco(@RequestBody @Valid EnderecoCreateDTO dto,@PathVariable Integer idEndereco) throws RegraDeNegocioException ;

    @Operation(summary = "Deleção de um endereço", description = "Deleção de um endereço buscado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleção de um endereço buscado por ID realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na deletar os dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void>deletar(Integer idEndereco) throws RegraDeNegocioException;
}
