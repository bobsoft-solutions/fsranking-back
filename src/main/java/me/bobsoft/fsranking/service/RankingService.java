package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.RankingDTO;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.Trend;
import me.bobsoft.fsranking.repository.CategoryRepository;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<RankingDTO> findPlayerAndSummaryScore(String category) {

        Iterable<Integer> playersIdOfWantedCategory = findPlayersIdFromScoresOfExactCategory(category);
        ArrayList<RankingDTO> rankingDTOS = new ArrayList<>();

        for (Integer playerId : playersIdOfWantedCategory) {

            if (playerRepository.findById(playerId).isPresent()) {
                Player player = playerRepository.findById(playerId).get();
                List<Score> scores = scoreRepository.findScoresByPlayerId(playerId);
                Integer points = 0;
                for (Score score : scores) {
                    points += score.getScore();
                }

                Integer categoryId = categoryRepository.findByName(category).getId();
                RankingDTO rankingDTO = new RankingDTO(player, points, getPlayerTrend(playerId, categoryId), null);
                rankingDTOS.add(rankingDTO);
            }
        }

        Collections.sort(rankingDTOS);
        Collections.reverse(rankingDTOS);
        for (int i = 0; i < rankingDTOS.size(); i++) {
            rankingDTOS.get(i).setPosition(i + 1);
        }

        return rankingDTOS;
    }

    private Trend getPlayerTrend(Integer playerId, Integer categoryId) {
        Trend trend;
        List<CumulatedPoint> cumulatedPoints =
                cumulatedPointRepository.findAllByIdPlayerAndCategoryId(playerId, categoryId)
                        .stream()
                        .sorted(Comparator.comparing(CumulatedPoint::getDate).reversed())
                        .collect(Collectors.toList());

        if (cumulatedPoints.size() < 2) {
            trend = Trend.SAME;
        } else {
            int earlierPoints = cumulatedPoints.get(1).getPlace();
            int latestPoints = cumulatedPoints.get(0).getPlace();

            if (latestPoints < earlierPoints)
                trend = Trend.UP;
            else if (latestPoints == earlierPoints)
                trend = Trend.SAME;
            else
                trend = Trend.DOWN;
        }

        return trend;
    }

    private List<Integer> findPlayersIdFromScoresOfExactCategory(String category) {
        List<Integer> playersId = new LinkedList<>();
        for (Score score : scoreRepository.findScoresByCategoryName(category)) {
            if (!playersId.contains(score.getPlayer().getId())) {
                playersId.add(score.getPlayer().getId());
            }
        }
        return playersId;
    }
}
