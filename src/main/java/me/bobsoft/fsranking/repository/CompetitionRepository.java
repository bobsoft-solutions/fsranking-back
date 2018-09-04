package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.Competition;
import me.bobsoft.fsranking.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    /* TODO: not sure if it will work, test it */
    List<Competition> findByLocation(Location location);
}