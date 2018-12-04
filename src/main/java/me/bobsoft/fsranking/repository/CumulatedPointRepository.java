package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CumulatedPointRepository extends JpaRepository<CumulatedPoint, Integer> {
    List<CumulatedPoint> findAll();
}
