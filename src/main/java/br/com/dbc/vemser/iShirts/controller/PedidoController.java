package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.PedidoControllerInterface;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.PedidoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/pedido")
public class PedidoController implements PedidoControllerInterface {
    private PedidoService pedidoService;
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> listarPedidoPorId(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.listarPedidoPorId(idPedido), HttpStatus.OK);
    }

    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<Page<PedidoDTO>> listarPedidosPorIdPessoa(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                    @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pedidoService.listarPedidosPorIdPessoa(idPessoa, pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) throws RegraDeNegocioException {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pedidoService.listarPedidos(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody @Valid PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.criarPedido(pedidoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> editarPedido(@PathVariable("idPedido") Integer idPedido, @RequestBody @Valid PedidoUpdateDTO pedidoUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.editarPedido(idPedido, pedidoUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> excluirPedido(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException {
        pedidoService.deletarPedido(idPedido);
        return ResponseEntity.ok().build();
    }

}
