package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.Ranking.Ranking;
import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.repository.CompetitionRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RankingService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public Iterable<Ranking> findPlayerAndSummaryScore(String category) {

        Iterable<Integer> playersOfWantedCategory = scoreRepository.findPlayersIdAppearingInBattleCategory();

        ArrayList<Ranking> rankings = new ArrayList<>();

        for (Integer playerId : playersOfWantedCategory) {

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
}
