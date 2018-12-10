package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.RankingDTO;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

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

                RankingDTO rankingDTO = new RankingDTO(player, points);
                rankingDTOS.add(rankingDTO);
            }
        }

        return rankingDTOS;
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

/* TODO: zrób tak żeby korzystać z:
    select id_player, max(points) from cumulated_point where id_category=1 group by id_player order by max(points) desc;
 */