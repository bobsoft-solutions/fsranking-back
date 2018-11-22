package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.Ranking.Ranking;
import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.repository.CompetitionRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public Iterable<Integer> findPlayerAndSummaryScore(String category) {

        Iterable<Score> scoresOfWantedCategory = scoreRepository.findByCategoryName(category);

        // find all unique players in table score
        Iterable<Integer> playersOfWantedCategory = scoreRepository.findPlayersIdAppearingInBattleCategory();

/*        Iterable<Player> players = playerRepository.findAll();

        players.forEach();
        // set them*/

        return playersOfWantedCategory;

    }
}
