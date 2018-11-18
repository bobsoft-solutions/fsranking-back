package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.player.CumulatedPoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CumulatedPointRepository extends CrudRepository<CumulatedPoint, Integer> {
//    public List<CumulatedPoint> findByCategoryName(String category);
    public List<CumulatedPoint> findAll();
}
