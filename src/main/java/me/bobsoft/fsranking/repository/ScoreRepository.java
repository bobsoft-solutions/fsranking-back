package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.Player;
import me.bobsoft.fsranking.model.Score;
import me.bobsoft.fsranking.model.ScoreIdClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, ScoreIdClass> {
    public List<Score> findByPlayerId(Integer id);
}
