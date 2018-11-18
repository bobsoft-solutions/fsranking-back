package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.model.player.PlayerPodiumCount;
import me.bobsoft.fsranking.model.player.PlayerStatistics;
import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Math.toIntExact;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Integer id) {
        Optional<Player> optionalPlayerl = playerRepository.findById(id);
        if(!optionalPlayerl.isPresent()) return optionalPlayerl;

        Player player = optionalPlayerl.get();
        player.setSummaryScores(new LinkedHashMap<String, Integer>());

        player.getSummaryScores().put("battle",
                scoreRepository.findByPlayerIdAndCategoryName(id, "battle")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        player.getSummaryScores().put("challenge",
                scoreRepository.findByPlayerIdAndCategoryName(id, "challenge")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        player.getSummaryScores().put("routine",
                scoreRepository.findByPlayerIdAndCategoryName(id, "routine")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        return Optional.of(player);
    }

    public PlayerStatistics findStatistics(Integer id) {
        PlayerStatistics playerStatistics = new PlayerStatistics();
        playerStatistics.setBattle(
                countPlayerPodiums(id, "battle")
        );
        playerStatistics.setChallenge(
                countPlayerPodiums(id, "challenge")
        );
        playerStatistics.setRoutine(
                countPlayerPodiums(id, "routine")
        );

        return playerStatistics;
    }

    public PlayerPodiumCount countPlayerPodiums(Integer playerId, String category) {
        PlayerPodiumCount playerPodiumCount = new PlayerPodiumCount();
        List<Score> scores = scoreRepository.findByPlayerIdAndCategoryName(playerId, category);

        playerPodiumCount.setCountOf1st(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getPlace() == 1)
                        .count()
                )
        );
        playerPodiumCount.setCountOf2nd(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getPlace() == 2)
                        .count()
                )
        );
        playerPodiumCount.setCountOf3rd(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getPlace() == 3)
                        .count()
                )
        );

        playerPodiumCount.setPoints(cumulatedPointRepository.findAll());

        return playerPodiumCount;
    }
}
