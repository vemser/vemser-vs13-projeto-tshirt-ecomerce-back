package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemUpdateQuantidadeDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.dbc.vemser.iShirts.model.Carrinho;
import br.com.dbc.vemser.iShirts.service.CarrinhoService;

import javax.validation.Valid;


@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;


    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> buscarCarrinho() throws RegraDeNegocioException {
        CarrinhoDTO carrinho = carrinhoService.buscarCarrinho();
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping
    public ResponseEntity<CarrinhoDTO> createCarrinho(@Valid @RequestBody CarrinhoCreateDTO carrinho) throws RegraDeNegocioException {
        CarrinhoDTO carrinhoDTO = carrinhoService.criarCarrinho(carrinho);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> atualizarCarrinho(@PathVariable Integer id, @Valid  @RequestBody Carrinho carrinho) throws RegraDeNegocioException {
        CarrinhoDTO carrinhoAtualizado = carrinhoService.atualizarCarrinho(id, carrinho);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Integer id) throws RegraDeNegocioException {
        carrinhoService.deleteCarrinho(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> limparCarrinho() throws RegraDeNegocioException {
        carrinhoService.limparCarrinho();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/adicionar-item")
    public ResponseEntity<CarrinhoDTO> adicionarItemCarrinho(@Valid @RequestBody ItemCreateDTO itemCreateDTO) throws RegraDeNegocioException {
        CarrinhoDTO carrinho = carrinhoService.adicionarItemCarrinho(itemCreateDTO);
        return ResponseEntity.ok(carrinho);
    }

    @PutMapping("/atualiza-item/{idItem}")
    public ResponseEntity<CarrinhoDTO> atualizaQuantidadeItem(@PathVariable Integer idItem, @Valid  @RequestBody ItemUpdateQuantidadeDTO quantidadeDTO) throws RegraDeNegocioException {
        CarrinhoDTO carrinho = carrinhoService.atualizaQuantidadeItem(idItem, quantidadeDTO);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping("/remover-item/{idItem}")
    public ResponseEntity<CarrinhoDTO> removerItemCarrinho(@PathVariable Integer idItem) throws RegraDeNegocioException {
        CarrinhoDTO carrinho = carrinhoService.removerItemCarrinho(idItem);
        return ResponseEntity.ok(carrinho);
    }
}
