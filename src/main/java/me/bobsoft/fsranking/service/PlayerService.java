package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.PlayerCategoryStatistics;
import me.bobsoft.fsranking.model.utils.PlayerStatistics;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.toIntExact;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    // ----------------- /players ------------------------------------------------
    public Iterable<Player> findAll() {
        Iterable<Player> players = playerRepository.findAll();
        for (Player player : players) {
            LocalDate now = LocalDate.now();
            String currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));

            player.setAge(player.getAge() == null ?
                    null : Integer.parseInt(currentYear) - player.getAge());
        }
        return players;
    }

    // ----------------- /players/{id} -------------------------------------------
    public PlayerDTO findById(Integer id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        return optionalPlayer.map(player -> new PlayerDTO(player, countPodiumById(id))).orElse(null);
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

    // ----------------- /players/{id}/statistics -------------------------------
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
}
