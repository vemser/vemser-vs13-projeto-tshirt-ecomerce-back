package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.ProdutoControllerInterface;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
@Slf4j
@Validated

@Tag(name = "Produto", description = "Controller responsável pelas operações relacionadas à produto.")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping("/todos-produtos")
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(@PageableDefault(value = 5, page = 0, sort = "criado") Pageable page){
        return new ResponseEntity<>(produtoService.listarProdutos(page), HttpStatus.OK);
    }

    @GetMapping("/por-id/{id}")
    public ResponseEntity<ProdutoDTO> listarPorID(@PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.listarPorID(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody @Valid ProdutoCreateDTO produto){
        return new ResponseEntity<>(produtoService.criarProduto(produto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> editarProduto(@RequestBody @Valid ProdutoCreateDTO produto, @PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.editarProduto(produto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable("id") @NotNull Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.deletarProduto(id), HttpStatus.OK);
    }

}
