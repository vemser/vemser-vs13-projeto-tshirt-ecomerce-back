package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
