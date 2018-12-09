package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CumulatedPointRepository extends CrudRepository<CumulatedPoint, Integer> {
    List<CumulatedPoint> findAll();

    List<CumulatedPoint> findAllByIdPlayer(Integer id);

    List<CumulatedPoint> findCumulatedPointByIdPlayerAndCategoryName(Integer id, String categoryName);
}
