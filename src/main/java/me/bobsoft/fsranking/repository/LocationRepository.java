package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}