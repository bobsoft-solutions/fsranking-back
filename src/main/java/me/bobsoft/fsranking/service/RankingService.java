package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.RankingDTO;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.Trend;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

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

                RankingDTO rankingDTO = new RankingDTO(player, points, getPlayerTrend(playerId));
                rankingDTOS.add(rankingDTO);
            }
        }

        return rankingDTOS;
    }

    private Trend getPlayerTrend(Integer playerId) {
        Trend trend;
        List<CumulatedPoint> cumulatedPoints =
                cumulatedPointRepository.findAllByIdPlayer(playerId)
                        .stream()
                        .sorted(Comparator.comparing(CumulatedPoint::getDate).reversed())
                        .collect(Collectors.toList());

        if(cumulatedPoints.size() < 2) {
            trend = Trend.SAME;
        }
        else {
            int earlierPoints = cumulatedPoints.get(1).getPlace();
            int latestPoints = cumulatedPoints.get(0).getPlace();

            if(latestPoints > earlierPoints)
                trend = Trend.UP;
            else if(latestPoints == earlierPoints)
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
