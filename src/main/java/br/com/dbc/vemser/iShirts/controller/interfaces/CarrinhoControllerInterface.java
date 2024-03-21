package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemUpdateQuantidadeDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Carrinho", description = "Controller responsável pelas operações relacionadas à carrinho.")
public interface CarrinhoControllerInterface {
    @Operation(summary = "Listagem do carrinho do usuário logado", description = "Listagem do carrinho do usuário logado no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem do carrinho  realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @GetMapping
    public ResponseEntity<CarrinhoDTO> buscarCarrinho() throws RegraDeNegocioException;
    @Operation(summary = "Criação do carrinho do usuário logado", description = "Criação do carrinho do usuário logado no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação do carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PostMapping
    public ResponseEntity<CarrinhoDTO> createCarrinho(@Valid @RequestBody CarrinhoCreateDTO carrinho) throws RegraDeNegocioException;
    @Operation(summary = "Atualização do carrinho do usuário logado", description = "Atualiza o carrinho do usuário logado no sistema com os dados mais recentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização do carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PutMapping
    public ResponseEntity<CarrinhoDTO> atualizarCarrinho() throws RegraDeNegocioException;

    @Operation(summary = "Exclui um carrinho por id", description = "Exclui o carrinho do  sistema por id;")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão do carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Integer id) throws RegraDeNegocioException;
    @Operation(summary = "Limpa o carrinho do usuário logado", description = "Limpa o carrinho do usuário logado no sistema com os dados mais recentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho limpo com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @DeleteMapping()
    public ResponseEntity<Void> limparCarrinho() throws RegraDeNegocioException;
    @Operation(summary = "Adiciona item no carrinho do usuário logado", description = "Adiciona item no  carrinho do usuário logado no sistema com os dados mais recentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adição no carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })

    @PostMapping("/adicionar-item")
    public ResponseEntity<CarrinhoDTO> adicionarItemCarrinho(@Valid @RequestBody ItemCreateDTO itemCreateDTO) throws RegraDeNegocioException;
    @Operation(summary = "Atualização quantidade de um item no carrinho do usuário logado", description = "Atualização quantidade de um item no carrinho do usuário logado no sistema com os dados mais recentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização do carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @PutMapping("/atualiza-item/{idItem}")
    public ResponseEntity<CarrinhoDTO> atualizaQuantidadeItem(@PathVariable Integer idItem, @Valid  @RequestBody ItemUpdateQuantidadeDTO quantidadeDTO) throws RegraDeNegocioException;

    @Operation(summary = "Exclui item no carrinho do usuário logado", description = "Exclui item no carrinho do usuário logado no sistema com os dados mais recentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão do item no carrinho realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    @DeleteMapping("/remover-item/{idItem}")
    public ResponseEntity<CarrinhoDTO> removerItemCarrinho(@PathVariable Integer idItem) throws RegraDeNegocioException;
}
