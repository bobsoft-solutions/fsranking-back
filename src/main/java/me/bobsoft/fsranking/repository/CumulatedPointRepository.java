package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CumulatedPointRepository extends CrudRepository<CumulatedPoint, Integer> {
    List<CumulatedPoint> findAll();
}
