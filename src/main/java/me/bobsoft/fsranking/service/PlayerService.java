package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.model.player.PlayerCategoryStatistics;
import me.bobsoft.fsranking.model.player.PlayerHistory;
import me.bobsoft.fsranking.model.player.PlayerStatistics;
import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        optionalPlayer.ifPresent(p -> p.setSummaryScores(countPodiumById(id)));

        return optionalPlayer;
    }

    private Map<String, Integer> countPodiumById(Integer playerId) {
        Map<String, Integer> map = new LinkedHashMap<>();

        map.put("battle",
                scoreRepository.findByPlayerIdAndCategoryName(playerId, "battle")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        map.put("challenge",
                scoreRepository.findByPlayerIdAndCategoryName(playerId, "challenge")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        map.put("routine",
                scoreRepository.findByPlayerIdAndCategoryName(playerId, "routine")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse(0)
        );

        return map;
    }

    public PlayerStatistics findStatisticsById(Integer id) {
        PlayerStatistics playerStatistics = new PlayerStatistics();

        playerStatistics.setBattle(
                resolveStatisticsByIdAndCategory(id, "battle")
        );
        playerStatistics.setChallenge(
                resolveStatisticsByIdAndCategory(id, "challenge")
        );
        playerStatistics.setRoutine(
                resolveStatisticsByIdAndCategory(id, "routine")
        );

        return playerStatistics;
    }

    private PlayerCategoryStatistics resolveStatisticsByIdAndCategory(Integer playerId, String category) {
        PlayerCategoryStatistics playerPodiumCount = new PlayerCategoryStatistics();
        List<Score> scores = scoreRepository.findByPlayerIdAndCategoryName(playerId, category);

        playerPodiumCount.setCountOf1st(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getDefaultPoint().getId() == 1)
                        .count()
                )
        );
        playerPodiumCount.setCountOf2nd(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getDefaultPoint().getId() == 2)
                        .count()
                )
        );
        playerPodiumCount.setCountOf3rd(
                toIntExact(
                    scores.stream()
                        .filter(s -> s.getDefaultPoint().getId() == 3)
                        .count()
                )
        );

        playerPodiumCount.setPoints(cumulatedPointRepository.findAll());

        return playerPodiumCount;
    }

    public List<PlayerHistory> findHistoryById(Integer id) {
        return scoreRepository.findScoresByPlayerId(id)
                .stream()
                .map(s -> new PlayerHistory(s))
                .sorted(Comparator.comparing(PlayerHistory::getDate))
                .collect(Collectors.toList());
    }
}
