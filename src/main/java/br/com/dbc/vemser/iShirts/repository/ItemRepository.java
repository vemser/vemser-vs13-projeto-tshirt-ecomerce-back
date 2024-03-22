package br.com.dbc.vemser.iShirts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dbc.vemser.iShirts.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
