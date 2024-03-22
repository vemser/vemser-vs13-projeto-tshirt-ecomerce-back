package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dbc.vemser.iShirts.model.Carrinho;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer>{
    Carrinho findByUsuario(Usuario usuario);
}
