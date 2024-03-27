package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.PedidoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoService pedidoService;
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> listarPedidoPorId(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.listarPedidoPorId(idPedido), HttpStatus.OK);
    }

    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorIdPessoa(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.listarPedidosPorIdPessoa(idPessoa), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.listarPedidos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.criarPedido(pedidoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> editarPedido(@PathVariable("idPedido") Integer idPedido, @RequestBody PedidoUpdateDTO pedidoUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.editarPedido(idPedido, pedidoUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> editarPedido(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException {
        pedidoService.deletarPedido(idPedido);
        return ResponseEntity.ok().build();
    }

}
