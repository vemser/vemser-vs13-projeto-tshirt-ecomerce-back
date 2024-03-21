package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pedido;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private PessoaService pessoaService;
    private ObjectMapper objectMapper;

    public PedidoDTO listarPedidoPorId(Integer idPedido) throws RegraDeNegocioException {
        return retornaPedidoDTO(getPedido(idPedido));
    }

    public List<PedidoDTO> listarPedidosPorIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        List<PedidoDTO> pedidos = pedidoRepository.listPedidosPorPessoa(idPessoa).stream()
                .map(this::retornaPedidoDTO)
                .collect(Collectors.toList());

        pedidos.stream()
                .findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("Essa pessoa não possui nenhum pedido!"));
        return pedidos;
    }

    public List<PedidoDTO> listarPedidos(){
        return pedidoRepository.findAll().stream()
                .map(this::retornaPedidoDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO criarPedido(Integer idPessoa, PedidoCreateDTO pedidoCreateDTO){
        return null;
    }

    public PedidoDTO editarPedido(Integer idPedido, PedidoUpdateDTO pedidoUpdateDTO){
        return null;
    }

    public void deletarPedido(Integer idPedido){

    }

    private Pessoa getPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaService.buscarPessoaPorId(idPessoa);
    }

    private Pedido getPedido(Integer idPedido) throws RegraDeNegocioException {
        return pedidoRepository.findById(idPedido).
                orElseThrow(()-> new RegraDeNegocioException("Pedido não encontrado!"));
    }

    private PedidoDTO retornaPedidoDTO(Pedido pedido){
        return objectMapper.convertValue(pedido, PedidoDTO.class);
    }

    private Pedido convertPedidoUpdateDTO(PedidoUpdateDTO pedidoUpdateDTO){
        return objectMapper.convertValue(pedidoUpdateDTO, Pedido.class);
    }

    private Pedido convertPedidoCreateDTO(PedidoCreateDTO pedidoCreateDTO){
        return objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
    }
}
