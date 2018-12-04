package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.dto.ScoreDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.repository.CompetitionRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Optional<CompetitionDTO> findById(Integer id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        if(competition == null) return null;

        CompetitionDTO competitionDTO = new CompetitionDTO(competition);

        competitionDTO.setBattle(scoreRepository.findScoresByCompetitionIdAndCategoryName(competition.getId(), "battle")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));
        competitionDTO.setChallenge(scoreRepository.findScoresByCompetitionIdAndCategoryName(competition.getId(), "challenge")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));
        competitionDTO.setRoutine(scoreRepository.findScoresByCompetitionIdAndCategoryName(competition.getId(), "routine")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));

        return Optional.ofNullable(competitionDTO);
    }
}