package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ProdutoControllerInterface {

    @Operation(summary = "Lista todos os produtos paginado.", description = "Lista todos os produtos cadastrados e ativos paginados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping("/todos-produtos")
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(@Parameter(hidden = true, required = true) @PageableDefault(value = 5, page = 0, sort = "criado") Pageable page);



    @Operation(summary = "Lista produtos por ID.", description = "Lista produtos passando os ID escolhido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping("/por-id/{id}")
    public ResponseEntity<ProdutoDTO> listarPorID(@PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Cria um produto.", description = "Cria um produto no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody @Valid ProdutoCreateDTO produto);

    @Operation(summary = "Edita um produto.", description = "Edita um produto no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto editado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> editarProduto(@RequestBody @Valid ProdutoCreateDTO produto, @PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Deleta um produto.", description = "Deleta um produto no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException;




}
