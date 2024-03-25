package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    boolean existsByCpf(String cpf);

    Optional<Pessoa> findByCpf(String cpf);

    Pessoa findPessoaByUsuario(Usuario usuario);

}
