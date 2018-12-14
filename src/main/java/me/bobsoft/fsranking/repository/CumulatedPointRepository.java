package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CumulatedPointRepository extends JpaRepository<CumulatedPoint, Integer> {
    List<CumulatedPoint> findAll();

    List<CumulatedPoint> findCumulatedPointByIdPlayerAndCategoryName(Integer id, String categoryName);

    Optional<CumulatedPoint> findFirstByIdPlayerAndCategoryOrderByPointsDesc(Integer playerId, Category category);

    @Query("SELECT distinct cp.idPlayer from CumulatedPoint cp where cp.category.id=?1")
    List<Integer> getPlayersIdOfCategory(Integer categoryId);

    CumulatedPoint findFirstByCategoryIdAndIdPlayerOrderByDateDesc(Integer categoryId, Integer playerId);

    List<CumulatedPoint> findAllByIdPlayerAndCategoryId(Integer playerId, Integer categoryId);
}
