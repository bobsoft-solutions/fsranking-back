package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.dto.PlayerDTOforPlayersEndpoint;
import me.bobsoft.fsranking.model.dto.PlayerStatisticsDTO;
import me.bobsoft.fsranking.model.entities.*;
import me.bobsoft.fsranking.model.utils.CumulatedPointDTO;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;
import me.bobsoft.fsranking.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        playerRepository = mock(PlayerRepository.class);
        scoreRepository = mock(ScoreRepository.class);
        cumulatedPointRepository = mock(CumulatedPointRepository.class);
        socialMediaRepository = mock(SocialMediaRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        playerService = new PlayerService(playerRepository, scoreRepository,
                cumulatedPointRepository, socialMediaRepository, categoryRepository);
    }

    @Test
    public void findAllTest() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList());

        List<Player> players = playerService.findAll();

        assertThat(players).isEmpty();
        Mockito.verify(playerRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest2() {
        Player playerA = new Player();
        Player playerB = new Player();

        when(playerRepository.findAll()).thenReturn(Arrays.asList(playerA, playerB));

        List<Player> players = playerService.findAll();

        assertThat(players).containsExactly(playerA, playerB);
        Mockito.verify(playerRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllPlayers() {
        Player player = new Player();

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player));

        List<PlayerDTOforPlayersEndpoint> players = playerService.findAllPlayers();

        assertThat(players.get(0).getClass()).isEqualTo(PlayerDTOforPlayersEndpoint.class);
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
        Category category = new Category();
        category.setId(1);
        category.setName("xyz");

        Integer playerId = 12;
        Player player = new Player();
        player.setId(playerId);

        Score scoreA = new Score();
        scoreA.setScore(15);

        Score scoreB = new Score();
        scoreB.setScore(30);

        CumulatedPoint cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setCategory(category);
        cumulatedPoint.setIdPlayer(playerId);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "battle"))
                .thenReturn(Arrays.asList(scoreA, scoreB));
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "challenge"))
                .thenReturn(new ArrayList<>());
        when(scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "routine"))
                .thenReturn(new ArrayList<>());
        when(cumulatedPointRepository.findAllByIdPlayerAndCategoryId(playerId, category.getId()))
                .thenReturn(Arrays.asList(cumulatedPoint));
        when(cumulatedPointRepository.getPlayersIdOfCategory(category.getId()))
                .thenReturn(Arrays.asList(playerId));

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

    // Atom props are updated
    @Test
    public void putPlayerTest() {
        Integer playerId = 5;
        Player player = new Player();
        player.setId(playerId);
        player.setNick("Stefan");

        String newNick = "Simon";
        Player updatedPlayer = new Player();
        updatedPlayer.setNick(newNick);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        ResponseEntity<Player> entity = playerService.putPlayer(playerId, updatedPlayer);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(player);
        assertThat(entity.getBody().getNick()).isEqualTo(newNick);
    }

    // returns HTTP 404 on wrong url
    @Test
    public void putPlayerTest2() {
        Integer playerId = 2000;

        Player updatedPlayer = new Player();

        when(playerRepository.findById(playerId)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Player> entity = playerService.putPlayer(playerId, updatedPlayer);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Adds social media if not added already
    @Test
    public void putPlayerTest3() {
        Integer playerId = 8;
        Player player = new Player();
        player.setId(playerId);

        String facebookURL = "fcb.com";
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setFacebookURL(facebookURL);

        Player updatedPlayer = new Player();
        updatedPlayer.setSocialMedia(socialMedia);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        ResponseEntity<Player> entity = playerService.putPlayer(playerId, updatedPlayer);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(player);
        assertThat(entity.getBody().getSocialMedia().getPlayerId()).isEqualTo(playerId);
        assertThat(entity.getBody().getSocialMedia().getFacebookURL()).isEqualTo(facebookURL);
        assertThat(entity.getBody().getSocialMedia().getInstagramURL()).isNull();
        assertThat(entity.getBody().getSocialMedia().getYoutubeURL()).isNull();
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