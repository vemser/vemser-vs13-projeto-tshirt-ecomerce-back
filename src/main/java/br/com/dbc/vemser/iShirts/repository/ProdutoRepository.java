package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Page<Produto> findAllByAtivo(Pageable pageable, String ativo);
}
