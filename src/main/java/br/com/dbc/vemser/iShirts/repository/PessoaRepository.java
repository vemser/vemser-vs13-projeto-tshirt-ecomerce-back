package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
}
