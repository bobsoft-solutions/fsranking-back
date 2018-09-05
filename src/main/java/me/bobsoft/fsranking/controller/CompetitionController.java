package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.Competition;
import me.bobsoft.fsranking.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/competitions")
    public List<Competition> findAllCompetitions() {
        return competitionService.findAll();
    }
}
