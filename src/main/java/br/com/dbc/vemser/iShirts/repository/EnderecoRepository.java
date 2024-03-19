package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
