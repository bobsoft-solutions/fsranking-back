package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.utils.ScoreDTO;
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

    /* ---------------------------- POST ------------------------------------------------------- */
    public void addCompetition(Competition competition) {
        competitionRepository.save(competition);
    }

    /* ---------------------------- GET -------------------------------------------------------- */
    public List<CompetitionDTO> findAll() {
        return competitionRepository.findAll()
                .stream()
                .map(CompetitionDTO::new)
                .map(this::setScoresByCategory)
                .collect(Collectors.toList());
    }

    public Optional<CompetitionDTO> findById(Integer id) {
        if(!competitionRepository.findById(id).isPresent()) return Optional.ofNullable(null);

        Competition competition = competitionRepository.findById(id).get();

        return Optional.ofNullable(setScoresByCategory(new CompetitionDTO(competition)));
    }

    private CompetitionDTO setScoresByCategory(CompetitionDTO competitionDTO) {
        competitionDTO.setBattle(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionDTO.getId(), "battle")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));
        competitionDTO.setChallenge(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionDTO.getId(), "challenge")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));
        competitionDTO.setRoutine(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionDTO.getId(), "routine")
                .stream()
                .map(ScoreDTO::new)
                .collect(Collectors.toList()));
        return competitionDTO;
    }
}