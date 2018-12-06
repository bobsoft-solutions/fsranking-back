package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/competitions")
    public List<CompetitionDTO> findAllCompetitions() {
        return competitionService.findAll();
    }

    @GetMapping("/competitions/{id}")
    public Optional<CompetitionDTO> findPlayerById(@PathVariable Integer id) {
        return competitionService.findById(id);
    }

    @PostMapping("/competitions")
    public ResponseEntity addCompetition(@RequestBody Competition competition) {

        System.out.println(competition.getId());
        System.out.println(competition.getName());
        System.out.println(competition.getLocation());
        System.out.println(competition.getYear());
        System.out.println(competition.getImportance());
        System.out.println(competition.getGroup());

        competitionService.addCompetition(competition);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
