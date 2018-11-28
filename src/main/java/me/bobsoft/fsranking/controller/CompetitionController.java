package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/competitions")
    public List<Competition> findAllCompetitions() {
        return competitionService.findAll();
    }

    @GetMapping("/competitions/{id}")
    public Optional<Competition> findPlayerById(@PathVariable Integer id) {
        return competitionService.findById(id);
    }
}
