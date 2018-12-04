package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    public List<Competition> findAll();
}