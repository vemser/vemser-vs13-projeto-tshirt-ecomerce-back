package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedido", description = "Controller responsável pelas operações relacionadas à Pedido.")
public interface PedidoControllerInterface {
    @Operation(summary = "Listar Pedido por ID", description = "Este endpoint retorna um pedido com base no ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o pedido"),
                    @ApiResponse(responseCode = "400", description = "Erro na inserção de dados ou Pedido não encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            }
    )
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> listarPedidoPorId(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException;

    @Operation(summary = "Listar Pedidos por ID de Pessoa", description = "Este endpoint retorna todos os pedidos com base no ID pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
                    @ApiResponse(responseCode = "400", description = "Erro na inserção de dados ou pessoa não possui nenhum pedido."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            }
    )
    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<Page<PedidoDTO>> listarPedidosPorIdPessoa(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                    @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException;

    @Operation(summary = "Listar Pedidos", description = "Este endpoint retorna todos os pedidos.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso")
            }
    )
    @GetMapping
        public ResponseEntity<Page<PedidoDTO>> listarPedidos(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(required = false, defaultValue = "10") Integer size) throws RegraDeNegocioException;
    @Operation(summary = "Criar Pedido", description = "Este endpoint cria um novo pedido.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na inserção de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso")
            }
    )
    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException ;

    @Operation(summary = "Editar Pedido", description = "Este endpoint edita um pedido com base no ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pedido editado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na inserção de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            }
    )
    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> editarPedido(@PathVariable("idPedido") Integer idPedido, @RequestBody PedidoUpdateDTO pedidoUpdateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Excluir Pedido", description = "Este endpoint exclui um pedido com base no ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pedido excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na inserção de dados ou Pedido não encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            }
    )
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> excluirPedido(@PathVariable("idPedido") Integer idPedido) throws RegraDeNegocioException;

}
