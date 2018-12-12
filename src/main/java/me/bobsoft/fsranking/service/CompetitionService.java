package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.DefaultPoint;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.CompetitionWithScoringPlayers;
import me.bobsoft.fsranking.model.utils.ScoreDTO;
import me.bobsoft.fsranking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    /* ---------------------------- POST ------------------------------------------------------- */
    public void addCompetition(CompetitionWithScoringPlayers competitionWithScoringPlayers) {
        Competition competition = competitionRepository.save(competitionWithScoringPlayers.getCompetition());

        updateScoreTable(competition, competitionWithScoringPlayers);

        updatePlaceColumnInCumulatedPoint(
                competitionWithScoringPlayers.getCategoryId(),
                competition.getDate(),
                competitionWithScoringPlayers.getScoringPlayersId());
    }

    private void updateScoreTable(Competition competition,
                                  CompetitionWithScoringPlayers competitionWithScoringPlayers) {

        List<Integer> playersId = competitionWithScoringPlayers.getScoringPlayersId();
        for (int i = 0; i < playersId.size(); i++) {
            Score score = new Score();

            score.setPlayer(playerRepository.findById(playersId.get(i)).get());

            score.setCompetition(competitionRepository.findById(competition.getId()).get());

            DefaultPoint defaultPoint = defaultPointRepository.findById(i + 1).get();
            score.setDefaultPoint(defaultPoint);

            score.setCategory(categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get());

            Integer competitionImportance = competitionRepository.findById(competition.getId()).get().getImportance();
            Integer weightOfPlace = defaultPointRepository.findById(defaultPoint.getId()).get().getValue();
            score.setScore(competitionImportance * weightOfPlace);

            scoreRepository.save(score);
            updateCumulatedPoint(competition, competitionWithScoringPlayers, playersId.get(i), i + 1);
        }
    }

    private void updateCumulatedPoint(Competition competition,
                                      CompetitionWithScoringPlayers competitionWithScoringPlayers,
                                      Integer playerId,
                                      Integer placeOnPodium) {

        CumulatedPoint cumulatedPoint = new CumulatedPoint();

        /* id_category */
        cumulatedPoint.setCategory(categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get());

        /* date */
        cumulatedPoint.setDate(competition.getDate());

        /* id_player */
        cumulatedPoint.setIdPlayer(playerId);

        /* id_points */
        Integer pointsBefore = 0;
        if (categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).isPresent()) {
            if (cumulatedPointRepository
                    .findFirstByIdPlayerAndCategoryOrderByPointsDesc(
                            playerId,
                            categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get())
                    .isPresent()) {
                pointsBefore = cumulatedPointRepository
                        .findFirstByIdPlayerAndCategoryOrderByPointsDesc(
                                playerId,
                                categoryRepository.findById(competitionWithScoringPlayers.getCategoryId()).get())
                        .get().getPoints();
            }
        }
        Integer pointsToAdd = defaultPointRepository.findById(placeOnPodium).get().getValue() * competition.getImportance();
        cumulatedPoint.setPoints(pointsBefore + pointsToAdd);

        /* place - here is null because it is set up by updatePlaceColumnInCumulatedPoint() */
        cumulatedPoint.setPlace(null);

        cumulatedPointRepository.save(cumulatedPoint);
    }

    private void updatePlaceColumnInCumulatedPoint(Integer categoryId, Date date, List<Integer> scoringPlayersId) {
        List<Integer> playersId = cumulatedPointRepository.getPlayersIdOfCategory(categoryId);

        List<CumulatedPoint> latestCumulatedPoints = new LinkedList<>();
        for (Integer playerId : playersId) {
            latestCumulatedPoints.add(cumulatedPointRepository.findFirstByCategoryIdAndIdPlayerOrderByDateDesc(categoryId, playerId));
        }

        Collections.sort(latestCumulatedPoints);
        Collections.reverse(latestCumulatedPoints);

        for (int i = 0; i < latestCumulatedPoints.size(); i++) {

            if (scoringPlayersId.contains(latestCumulatedPoints.get(i).getIdPlayer())) {
                CumulatedPoint nowScoring = latestCumulatedPoints.get(i);
                nowScoring.setPlace(i + 1);
                cumulatedPointRepository.save(nowScoring);
            } else {
                CumulatedPoint notNowScoring = new CumulatedPoint(latestCumulatedPoints.get(i));
                notNowScoring.setPlace(i + 1);
                notNowScoring.setDate(date);
                cumulatedPointRepository.save(notNowScoring);
            }
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


    public void deleteCompetition(Integer competitionId) {

        if (competitionRepository.findById(competitionId).isPresent()) {
            competitionRepository.delete(competitionRepository.findById(competitionId).get());
        }
    }

    public void editCompetition(CompetitionWithScoringPlayers competitionWithScoringPlayers, Integer id) {

        // edit competition table
        if (competitionRepository.findById(id).isPresent()) {
            Competition competition = competitionRepository.findById(id).get();
            competition.setDate(competitionWithScoringPlayers.getCompetition().getDate());
            competition.setGroup(competitionWithScoringPlayers.getCompetition().getGroup());
            competition.setImportance(competitionWithScoringPlayers.getCompetition().getImportance());
            competition.setLocation(competitionWithScoringPlayers.getCompetition().getLocation());
            competition.setName(competitionWithScoringPlayers.getCompetition().getName());

            competitionRepository.save(competition);


            // edit score table
            List<Score> scores = scoreRepository.findScoresByCompetitionIdAndCategoryId(
                    competition.getId(),
                    competitionWithScoringPlayers.getCategoryId());
            // TODO

            // edit cumulated_point table
        }
    }
}