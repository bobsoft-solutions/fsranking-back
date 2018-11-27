package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> findById(Integer id) {
        return competitionRepository.findById(id);
    }
}