package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.utils.CompetitionWithScoringPlayers;
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
    public ResponseEntity addCompetition(@RequestBody CompetitionWithScoringPlayers competitionWithScoringPlayers) {

        competitionService.addCompetition(competitionWithScoringPlayers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/competitions/{id}")
    public ResponseEntity deleteCompetition(@PathVariable Integer id) {

        competitionService.deleteCompetition(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PutMapping("/competitions/{id}")
    public ResponseEntity editCompetition(@RequestBody CompetitionWithScoringPlayers competitionWithScoringPlayers,
                                          @PathVariable Integer id) {

        competitionService.editCompetition(competitionWithScoringPlayers, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
