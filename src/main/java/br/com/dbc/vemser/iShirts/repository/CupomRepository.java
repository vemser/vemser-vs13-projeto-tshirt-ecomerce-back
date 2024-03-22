package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {
}
