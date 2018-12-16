package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.dto.PlayerDTOforPlayersEndpoint;
import me.bobsoft.fsranking.model.dto.PlayerStatisticsDTO;
import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.SocialMedia;
import me.bobsoft.fsranking.model.utils.CumulatedPointDTO;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;
import me.bobsoft.fsranking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private ScoreRepository scoreRepository;
    private CumulatedPointRepository cumulatedPointRepository;
    private SocialMediaRepository socialMediaRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository,
                         ScoreRepository scoreRepository,
                         CumulatedPointRepository cumulatedPointRepository,
                         SocialMediaRepository socialMediaRepository,
                         CategoryRepository categoryRepository) {

        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.cumulatedPointRepository = cumulatedPointRepository;
        this.socialMediaRepository = socialMediaRepository;
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public List<PlayerDTOforPlayersEndpoint> findAllPlayers() {

        return playerRepository.findAll()
                .stream()
                .map(PlayerDTOforPlayersEndpoint::new)
                .collect(Collectors.toList());
    }

    public PlayerDTO postPlayer(Player player) {
        SocialMedia socialMedia = player.getSocialMedia();
        player.setSocialMedia(null);
        Player savedPlayer = playerRepository.saveAndFlush(player);

        if (socialMedia != null) {
            socialMedia.setPlayerId(savedPlayer.getId());
            socialMediaRepository.saveAndFlush(socialMedia);
            savedPlayer.setSocialMedia(socialMedia);
        }

        return new PlayerDTO(savedPlayer, null, null);
    }

    public PlayerDTO findById(Integer id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        return optionalPlayer.map(player -> new PlayerDTO(player, countPodiumById(id), countPositionById(id))).orElse(null);
    }

    private Map<String, Integer> countPositionById(Integer playerId) {

        Map<String, Integer> map = new LinkedHashMap<>();

        for (Category category : categoryRepository.findAll()) {

            List<Integer> playersIdOfExactCategory = cumulatedPointRepository.getPlayersIdOfCategory(category.getId());
            List<CumulatedPoint> allPlayersBestCumulatedPointList = new LinkedList<>();

            for (Integer player : playersIdOfExactCategory) {
                allPlayersBestCumulatedPointList.add(
                        Collections.max(cumulatedPointRepository.findAllByIdPlayerAndCategoryId(player, category.getId()))
                );
            }

            Collections.sort(allPlayersBestCumulatedPointList);
            Collections.reverse(allPlayersBestCumulatedPointList);

            for (int i = 0; i < allPlayersBestCumulatedPointList.size(); i++) {
                if (allPlayersBestCumulatedPointList.get(i).getIdPlayer().equals(playerId)) {
                    map.put(category.getName(), i + 1);
                }
            }
        }

        return map;
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

        playerStatisticsDTO.setScoreHistory(findHistoryById(id));

        return playerStatisticsDTO;
    }

    private List<CumulatedPointDTO> resolveStatisticsByIdAndCategory(Integer playerId, String category) {
        return cumulatedPointRepository.findCumulatedPointByIdPlayerAndCategoryName(playerId, category)
                .stream()
                .map(CumulatedPointDTO::new)
                .sorted(Comparator.comparing(CumulatedPointDTO::getDate))
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
