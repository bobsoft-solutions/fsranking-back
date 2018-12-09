package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.DefaultPoint;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.CompetitionWithScoringPlayers;
import me.bobsoft.fsranking.model.utils.ScoreDTO;
import me.bobsoft.fsranking.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompetitionService {

    Logger log = LoggerFactory.getLogger(CompetitionService.class);

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

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    /* ---------------------------- POST ------------------------------------------------------- */
    public void addCompetition(CompetitionWithScoringPlayers competitionWithScoringPlayers) {
        Competition competition = competitionRepository.save(competitionWithScoringPlayers.getCompetition());

        System.out.println(competition);
        updateScoreTable(competition, competitionWithScoringPlayers);
    }

    private void updateCumulatedPoint(Competition competition,
                                      CompetitionWithScoringPlayers competitionWithScoringPlayers,
                                      Integer playerId) {

        CumulatedPoint cumulatedPoint = new CumulatedPoint();

        /* id_category */
        cumulatedPoint.setCategory(categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get());

        /* date */
        cumulatedPoint.setDate(competition.getDate());

        /* id_player */
        cumulatedPoint.setIdPlayer(playerId);

        /* id_points */
        // TODO

        /* place */
        // TODO

    }

    private void updateScoreTable(Competition competition,
                                  CompetitionWithScoringPlayers competitionWithScoringPlayers) {

        List<Integer> playersId = competitionWithScoringPlayers.getScoringPlayersId();
        for (int i = 0; i < playersId.size(); i++) {
            Score score = new Score();
            log.info("1");

            score.setPlayer(playerRepository.findById(playersId.get(i)).get());
            log.info("2");
            score.setCompetition(competitionRepository.findById(competition.getId()).get());

            log.info("3");
            DefaultPoint defaultPoint = defaultPointRepository.findById(i+1).get();
            score.setDefaultPoint(defaultPoint);
            log.info("4");
            score.setCategory(categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get());
            log.info("5");
            Integer competitionImportance = competitionRepository.findById(competition.getId()).get().getImportance();
            Integer weightOfPlace = defaultPointRepository.findById(defaultPoint.getId()).get().getValue();
            score.setScore(competitionImportance * weightOfPlace);

            scoreRepository.save(score);

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