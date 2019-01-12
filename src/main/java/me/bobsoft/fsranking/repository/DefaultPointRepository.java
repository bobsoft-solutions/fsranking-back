package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.DefaultPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultPointRepository extends JpaRepository<DefaultPoint, Integer> {
}
