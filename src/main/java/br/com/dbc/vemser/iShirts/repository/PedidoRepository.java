package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Pedido;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query(value = """
            SELECT p FROM PEDIDO p
            WHERE p.pessoa = ?1
            """)
    List<Pedido> listPedidosPorPessoa(Pessoa pessoa);
}

