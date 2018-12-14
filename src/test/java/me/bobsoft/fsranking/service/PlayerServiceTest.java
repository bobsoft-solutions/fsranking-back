package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.dto.PlayerStatisticsDTO;
import me.bobsoft.fsranking.model.entities.*;
import me.bobsoft.fsranking.model.utils.CumulatedPointDTO;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import me.bobsoft.fsranking.repository.SocialMediaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {

    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private ScoreRepository scoreRepository;
    private CumulatedPointRepository cumulatedPointRepository;
    private SocialMediaRepository socialMediaRepository;

    @Before
    public void setUp() {
        playerRepository = mock(PlayerRepository.class);
        scoreRepository = mock(ScoreRepository.class);
        cumulatedPointRepository = mock(CumulatedPointRepository.class);
        socialMediaRepository = mock(SocialMediaRepository.class);

        playerService = new PlayerService(playerRepository, scoreRepository,
                cumulatedPointRepository, socialMediaRepository);
    }

    @Test
    public void findAllTest() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList());

        Iterable<Player> players = playerService.findAll();

        assertThat(players).isEmpty();
        Mockito.verify(playerRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest2() {
        Player playerA = new Player();
        Player playerB = new Player();

        when(playerRepository.findAll()).thenReturn(Arrays.asList(playerA, playerB));

        Iterable<Player> players = playerService.findAll();

        assertThat(players).containsExactly(playerA, playerB);
        Mockito.verify(playerRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void postPlayerTest() {
        Integer playerId = 18;

        SocialMedia socialMedia = new SocialMedia();
        Player player = new Player();
        player.setSocialMedia(socialMedia);

        Player playerReturned = new Player();
        playerReturned.setId(playerId);

        when(playerRepository.saveAndFlush(player)).thenReturn(playerReturned);

        PlayerDTO playerDTO = playerService.postPlayer(player);

        assertThat(playerDTO.getClass()).isEqualTo(PlayerDTO.class);
        assertThat(playerDTO.getId()).isEqualTo(playerId);
        assertThat(playerDTO.getSocialMedia()).isEqualTo(socialMedia);

        Mockito.verify(playerRepository, Mockito.times(1)).saveAndFlush(player);
        Mockito.verify(socialMediaRepository, Mockito.times(1)).saveAndFlush(socialMedia);
    }

    @Test
    public void postPlayerTest2() {
        Integer playerId = 123;

        Player player = new Player();

        Player playerReturned = new Player();
        playerReturned.setId(playerId);

        when(playerRepository.saveAndFlush(player)).thenReturn(playerReturned);

        PlayerDTO playerDTO = playerService.postPlayer(player);

        assertThat(playerDTO.getClass()).isEqualTo(PlayerDTO.class);
        assertThat(playerDTO.getId()).isEqualTo(playerId);
        assertThat(playerDTO.getSocialMedia()).isEqualTo(null);

        Mockito.verify(playerRepository, Mockito.times(1)).saveAndFlush(player);
        Mockito.verify(socialMediaRepository, Mockito.times(0)).saveAndFlush(null);
    }

    @Test
    public void findByIdTest() {
        Integer invalidPlayerId = -5;

        when(playerRepository.findById(invalidPlayerId)).thenReturn(Optional.ofNullable(null));

        PlayerDTO playerDTO = playerService.findById(invalidPlayerId);

        assertThat(playerDTO).isNull();
    }

    @Test
    public void findByIdTest2() {
        Integer playerId = 12;
        Player player = new Player();
        player.setId(playerId);

        Score scoreA = new Score();
        scoreA.setScore(15);

        Score scoreB = new Score();
        scoreB.setScore(30);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "battle"))
                .thenReturn(Arrays.asList(scoreA, scoreB));
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "challenge"))
                .thenReturn(new ArrayList<>());
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "routine"))
                .thenReturn(new ArrayList<>());

        PlayerDTO playerDTO = playerService.findById(playerId);

        assertThat(playerDTO).isNotNull();
        assertThat(playerDTO.getClass()).isEqualTo(PlayerDTO.class);
        assertThat(playerDTO.getSummaryScores().get("battle")).isEqualTo(45);
        assertThat(playerDTO.getSummaryScores().get("challenge")).isEqualTo(0);
        assertThat(playerDTO.getSummaryScores().get("routine")).isEqualTo(0);
    }

    @Test
    public void deletePlayerTest() {
        Integer playerId = 2;

        Mockito.doNothing().when(playerRepository).deleteById(playerId);

        playerService.deletePlayer(playerId);

        Mockito.verify(playerRepository, Mockito.times(1)).deleteById(playerId);
    }

    @Test
    public void findStatisticsByIdTest() {
        Integer playerId = 13;

        CumulatedPoint cumulatedPointA = new CumulatedPoint();
        cumulatedPointA.setDate(new Date());
        CumulatedPoint cumulatedPointB = new CumulatedPoint();
        cumulatedPointB.setDate(new Date());

        Competition competitionA = new Competition();

        try {
            competitionA.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2010-12-11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Category category = new Category();
        category.setName("battle");

        DefaultPoint defaultPoint = new DefaultPoint();
        defaultPoint.setId(1);

        Score scoreA = new Score();
        scoreA.setCompetition(competitionA);
        scoreA.setCategory(category);
        scoreA.setDefaultPoint(defaultPoint);


        when(cumulatedPointRepository.findCumulatedPointByIdPlayerAndCategoryName(playerId, "battle"))
                .thenReturn(Arrays.asList(cumulatedPointA, cumulatedPointB));
        when(cumulatedPointRepository.findCumulatedPointByIdPlayerAndCategoryName(playerId, "challenge")).thenReturn(Arrays.asList());
        when(cumulatedPointRepository.findCumulatedPointByIdPlayerAndCategoryName(playerId, "routine")).thenReturn(Arrays.asList());
        when(scoreRepository.findScoresByPlayerId(playerId))
                .thenReturn(Arrays.asList(scoreA));

        PlayerStatisticsDTO playerStatisticsDTO = playerService.findStatisticsById(playerId);

        assertThat(playerStatisticsDTO).isNotNull();
        assertThat(playerStatisticsDTO.getBattleCumulatedPoint()).hasSize(2);
        assertThat(playerStatisticsDTO.getBattleCumulatedPoint().get(0).getClass()).isEqualTo(CumulatedPointDTO.class);
        assertThat(playerStatisticsDTO.getScoreHistory()).hasSize(1);
        assertThat(playerStatisticsDTO.getScoreHistory().get(0).getClass()).isEqualTo(PlayerScoreDTO.class);
    }
}