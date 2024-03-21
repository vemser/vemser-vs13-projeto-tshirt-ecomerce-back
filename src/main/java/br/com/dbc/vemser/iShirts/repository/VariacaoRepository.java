package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariacaoRepository extends JpaRepository<Variacao, Integer> {
}
