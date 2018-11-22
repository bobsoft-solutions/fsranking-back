package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.model.score.ScoreIdClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, ScoreIdClass> {
    List<Score> findByPlayerIdAndCategoryName(Integer id, String name);

    List<Score> findByCategoryName(String name);

    List<Score> findByPlayerId(Integer id);

    @Query(value="select distinct id_player from score join competition c on c.id=id_category where id_category=1", nativeQuery = true)
    List<Integer> findPlayersIdAppearingInBattleCategory();
}
