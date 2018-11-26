package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.Ranking.Ranking;
import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.model.score.Score;
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

    public Iterable<Ranking> findPlayerAndSummaryScore(String category) {

        //Iterable<Integer> playersIdOfWantedCategory = scoreRepository.findPlayersIdAppearingInBattleCategory();

        Iterable<Integer> playersIdOfWantedCategory = findPlayersIdFromScoresOfExactCategory(category);
        ArrayList<Ranking> rankings = new ArrayList<>();

        for (Integer playerId : playersIdOfWantedCategory) {

            if (playerRepository.findById(playerId).isPresent()) {
                Player player = playerRepository.findById(playerId).get();
                List<Score> scores = scoreRepository.findScoresByPlayerId(playerId);
                Integer points = 0;
                for (Score score : scores) {
                    points += score.getScore();
                }

                Ranking ranking = new Ranking(player, points);
                rankings.add(ranking);
            }
        }

        return rankings;
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