package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private PessoaService pessoaService;
    private ObjectMapper objectMapper;

    public PedidoDTO criarPedido(Integer idPessoa, PedidoCreateDTO pedidoCreateDTO){

    }

    private Pessoa getPessoa(Integer idPessoa){
        pessoaService.buscarPessoaPorId()
    }
}
