package br.com.dbc.vemser.iShirts.repository;

import br.com.dbc.vemser.iShirts.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {

}
