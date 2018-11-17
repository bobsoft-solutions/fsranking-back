package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.model.score.ScoreIdClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, ScoreIdClass> {
    public List<Score> findByPlayerIdAndCategoryName(Integer id, String name);
}
