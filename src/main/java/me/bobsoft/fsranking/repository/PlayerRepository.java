package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
}