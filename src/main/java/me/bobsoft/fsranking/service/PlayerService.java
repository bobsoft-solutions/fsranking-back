package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.entities.SocialMedia;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;
import me.bobsoft.fsranking.model.dto.PlayerStatisticsDTO;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import me.bobsoft.fsranking.repository.SocialMediaRepository;
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

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    // ----------------- /players ------------------------------------------------
    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public PlayerDTO postPlayer(Player player) {
        SocialMedia socialMedia = player.getSocialMedia();
        player.setSocialMedia(null);
        Player savedPlayer = playerRepository.saveAndFlush(player);

        if(socialMedia != null) {
            socialMedia.setPlayerId(savedPlayer.getId());
            socialMediaRepository.saveAndFlush(socialMedia);
            savedPlayer.setSocialMedia(socialMedia);
        }

        return new PlayerDTO(savedPlayer, null);
    }

    // ----------------- /players/{id} -------------------------------------------
    public PlayerDTO findById(Integer id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        return optionalPlayer.map(player -> new PlayerDTO(player, countPodiumById(id))).orElse(null);
    }

    private Map<String, Integer> countPodiumById(Integer playerId) {
        Map<String, Integer> map = new LinkedHashMap<>();

        map.put("battle",
            scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "battle")
                .stream()
                .map(s -> s.getScore())
                .reduce((s1, s2) -> s1 + s2)
                .orElse(0)
        );

        map.put("challenge",
            scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "challenge")
                .stream()
                .map(s -> s.getScore())
                .reduce((s1, s2) -> s1 + s2)
                .orElse(0)
        );

        map.put("routine",
            scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "routine")
                .stream()
                .map(s -> s.getScore())
                .reduce((s1, s2) -> s1 + s2)
                .orElse(0)
        );

        return map;
    }

    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
    }

    // ----------------- /players/{id}/statistics -------------------------------
    public PlayerStatisticsDTO findStatisticsById(Integer id) {
        PlayerStatisticsDTO playerStatisticsDTO = new PlayerStatisticsDTO();

        playerStatisticsDTO.setBattleCumulatedPoint(
                resolveStatisticsByIdAndCategory(id, "battle")
        );
        playerStatisticsDTO.setChallengeCumulatedPoint(
                resolveStatisticsByIdAndCategory(id, "challenge")
        );
        playerStatisticsDTO.setRoutineCumulatedPoint(
                resolveStatisticsByIdAndCategory(id, "routine")
        );

        playerStatisticsDTO.setHistory(findHistoryById(id));

        return playerStatisticsDTO;
    }

    private List<CumulatedPoint> resolveStatisticsByIdAndCategory(Integer playerId, String category) {
        return cumulatedPointRepository.findCumulatedPointByIdPlayerAndCategoryName(playerId, category)
                .stream()
                .sorted(Comparator.comparing(CumulatedPoint::getDate))
                .collect(Collectors.toList());
    }

    private List<PlayerScoreDTO> findHistoryById(Integer id) {
        return scoreRepository.findScoresByPlayerId(id)
                .stream()
                .map(PlayerScoreDTO::new)
                .sorted(Comparator.comparing(PlayerScoreDTO::getDate))
                .collect(Collectors.toList());
    }
}
