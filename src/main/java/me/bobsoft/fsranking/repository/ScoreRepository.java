package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.ScoreIdClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, ScoreIdClass> {
    List<Score> findScoresByPlayerIdAndCategoryName(Integer id, String name);

    /* można by to zoptymalizować i pobierać Score dla unikalnych PlayerId -
       wtedy by można usunąć warunek if-a z
       RankingService.findPlayersIdFromScoresOfExactCategory(String)
     */
    List<Score> findScoresByCategoryName(String category);

    List<Score> findScoresByPlayerId(Integer id);
}