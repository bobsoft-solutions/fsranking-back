package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.DefaultPoint;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.CompetitionWithScoringPlayers;
import me.bobsoft.fsranking.model.utils.ScoreDTO;
import me.bobsoft.fsranking.repository.*;
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

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private DefaultPointRepository defaultPointRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /* ---------------------------- POST ------------------------------------------------------- */
    public void addCompetition(CompetitionWithScoringPlayers competitionWithScoringPlayers) {
        Competition competition = competitionRepository.save(competitionWithScoringPlayers.getCompetition());

        // update score
        updateScoreTable(competition, competitionWithScoringPlayers);

        // update cumulated_point

    }

    private void updateScoreTable(Competition competition,
                                  CompetitionWithScoringPlayers competitionWithScoringPlayers) {

        List<Integer> playersId = competitionWithScoringPlayers.getScoringPlayersId();
        for (Integer playerId : playersId) {
            Score score = new Score();

            score.setPlayer(playerRepository.findById(playerId).get());

            score.setCompetition(competitionRepository.findById(competition.getId()).get());

            DefaultPoint defaultPoint = defaultPointRepository.findById(playersId.indexOf(playerId)+1).get();
            score.setDefaultPoint(defaultPoint);

            score.setCategory(categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get());

            Integer competitionImportance = competitionRepository.findById(competition.getId()).get().getImportance();
            Integer weightOfPlace = defaultPointRepository.findById(defaultPoint.getId()).get().getValue();
            score.setScore(competitionImportance * weightOfPlace);

            //scoreRepository.save(score);

            /* TODO: change composite key in Score into one-attribute key */
        }
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
        if (!competitionRepository.findById(id).isPresent()) return Optional.ofNullable(null);

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