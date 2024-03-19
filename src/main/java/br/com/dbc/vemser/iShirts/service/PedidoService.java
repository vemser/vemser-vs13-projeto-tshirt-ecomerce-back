package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private ObjectMapper objectMapper;


}
