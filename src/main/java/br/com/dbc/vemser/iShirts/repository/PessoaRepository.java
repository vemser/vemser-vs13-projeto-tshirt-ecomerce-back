package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    boolean existsByCpf(String cpf);

    Optional<Pessoa> findByCpf(String cpf);
}
