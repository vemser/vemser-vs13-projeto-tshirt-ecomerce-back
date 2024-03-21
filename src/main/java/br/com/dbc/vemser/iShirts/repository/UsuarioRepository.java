package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByAtivo(Ativo ativo);

    List<Usuario> findAllByAtivo(Ativo ativo);

    Optional<Usuario> findByIdUsuarioAndAtivo(Integer idUsuario, Ativo ativo);

    boolean existsByEmail(String email);

    boolean existsByEmailAndAtivo(String email, Ativo ativo);

    Optional<Usuario> findByEmail(String email);
}

