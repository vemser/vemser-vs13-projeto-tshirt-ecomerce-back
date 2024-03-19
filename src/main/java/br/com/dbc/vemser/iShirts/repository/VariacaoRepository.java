package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Variacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariacaoRepository extends JpaRepository<Variacao, Integer> {
}
