package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.*;
import br.com.dbc.vemser.iShirts.model.enums.StatusPedido;
import br.com.dbc.vemser.iShirts.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
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

    public Page<PedidoDTO> listarPedidosPorIdPessoa(Integer idPessoa, Pageable page) throws RegraDeNegocioException {
        Page<Pedido> pagePedidos = pedidoRepository.listPedidosPorPessoa(getPessoa(idPessoa), page);

        return pagePedidos.map(this::retornaPedidoDTO);
    }

    public Page<PedidoDTO> listarPedidos(Pageable page){
       Page<Pedido> pagePedidos = pedidoRepository.findAllBy(page);
       return pagePedidos.map(this::retornaPedidoDTO);
    }

    public PedidoDTO criarPedido(PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.buscarUsuarioLogadoEntity();

        carrinhoService.atualizarCarrinho();
        Carrinho carrinho = carrinhoService.buscarCarrinhoUsuarioLogado();

        Pedido pedido = montarPedido(usuario, pedidoCreateDTO, carrinho);

        pedidoRepository.save(pedido);
        carrinhoService.limparCarrinhoPedidoFeito();

        return retornaPedidoDTO(pedido);
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

    private Pedido montarPedido(Usuario usuario, PedidoCreateDTO pedidoCreateDTO, Carrinho carrinho) throws RegraDeNegocioException {
        Pedido pedido = new Pedido();
        pedido.setPessoa(pessoaService.buscarPessoaPorUsuario(usuario));
        pedido.setMetodoPagamento(pedidoCreateDTO.getMetodoPagamento());
        pedido.setItens(new ArrayList<>(carrinho.getItens()));
        pedido.setTotalLiquido(validarCupom(carrinho.getTotal(), pedidoCreateDTO.getIdCupom()));
        Cupom cupom = getCupom(pedidoCreateDTO.getIdCupom());
        if (cupom != null) {
            pedido.setCupom(cupom);
        }
        pedido.setTotalBruto(carrinhoService.calcularValorBrutoTotal(carrinho));
        pedido.setDesconto(pedido.getTotalBruto().subtract(pedido.getTotalLiquido()));
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
        return pedido;
    }

    private Pessoa getPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaService.buscarPessoaPorId(idPessoa);
    }

    private Pedido getPedido(Integer idPedido) throws RegraDeNegocioException {
        return pedidoRepository.findById(idPedido).
                orElseThrow(()-> new RegraDeNegocioException("Pedido não encontrado!"));
    }

    private Cupom getCupom(Integer idCupom) throws RegraDeNegocioException {
        if (idCupom == 0){
            return null;
        }
        return cupomService.getCupom(idCupom);
    }

    private PedidoDTO retornaPedidoDTO(Pedido pedido){
        return objectMapper.convertValue(pedido, PedidoDTO.class);
    }
}
