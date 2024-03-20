package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Page<Endereco> findAllByIdPessoaIs(Integer idPessoa, Pageable pageable);
}
