package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.*;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import br.com.dbc.vemser.iShirts.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private PessoaService pessoaService;
    private CarrinhoService carrinhoService;
    private UsuarioService usuarioService;
    private CupomService cupomService;
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

    public PedidoDTO criarPedido(PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.buscarUsuarioLogadoEntity();

        carrinhoService.atualizarCarrinho();
        Carrinho carrinho = carrinhoService.buscarCarrinhoUsuarioLogado();

        Pedido pedido = new Pedido();
        pedido.setPessoa(pessoaService.buscarPessoaPorUsuario(usuario));
        pedido.setMetodoPagamento(pedidoCreateDTO.getMetodoPagamento());
        pedido.setItens(new ArrayList<>(carrinho.getItens()));
        pedido.setTotalLiquido(validarCupom(carrinho.getTotal(), pedidoCreateDTO.getIdCupom()));
        pedido.setCupom(getCupom(pedidoCreateDTO.getIdCupom()));
        pedido.setDesconto(pedido.getTotalBruto().subtract(pedido.getTotalLiquido()));
        pedido.setTotalBruto(carrinhoService.calcularValorBrutoTotal(carrinho));
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);

        carrinhoService.limparCarrinhoPedidoFeito();

        return retornaPedidoDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO editarPedido(Integer idPedido, PedidoUpdateDTO pedidoUpdateDTO) throws RegraDeNegocioException {
        Pedido pedidoEditado = getPedido(idPedido);
        pedidoEditado.setStatus(pedidoEditado.getStatus());
        pedidoEditado.setMetodoPagamento(pedidoUpdateDTO.getMetodoPagamento());

        return retornaPedidoDTO(pedidoRepository.save(pedidoEditado));
    }

    public void deletarPedido(Integer idPedido) throws RegraDeNegocioException {
        Pedido pedido = getPedido(idPedido);
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    private BigDecimal validarCupom(BigDecimal valorCarrinho, Integer idCupom) throws RegraDeNegocioException {
        Cupom cupom = getCupom(idCupom);
        if(cupom == null){
            return valorCarrinho;
        }

        if(valorCarrinho.compareTo(BigDecimal.valueOf(cupom.getValorMinimo())) < 0){
            throw new RegraDeNegocioException("O valor minimo é de R$: " + cupom.getValorMinimo());
        }

        return valorCarrinho.subtract(BigDecimal.valueOf(cupom.getValorMinimo()));
    }

    private Pessoa getPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaService.buscarPessoaPorId(idPessoa);
    }

    private Pedido getPedido(Integer idPedido) throws RegraDeNegocioException {
        return pedidoRepository.findById(idPedido).
                orElseThrow(()-> new RegraDeNegocioException("Pedido não encontrado!"));
    }

    private Cupom getCupom(Integer idCupom) throws RegraDeNegocioException {
        if (idCupom == null){
            return null;
        }
        return cupomService.getCupom(idCupom);
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
