package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import me.bobsoft.fsranking.repository.SocialMediaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PlayerService.class})
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private ScoreRepository scoreRepository;

    @MockBean
    private CumulatedPointRepository cumulatedPointRepository;

    @MockBean
    private SocialMediaRepository socialMediaRepository;

    @Test
    public void findHistoryByIdTest() {
        Competition competitionA = new Competition();
        competitionA.setYear(ZonedDateTime.parse("2010-12-11T00:00:00-00:00"));
        Score scoreA = new Score();
        scoreA.setCompetition(competitionA);

        Competition competitionB = new Competition();
        competitionB.setYear(ZonedDateTime.parse("2012-02-09T00:00:00-00:00"));
        Score scoreB = new Score();
        scoreB.setCompetition(competitionB);


        Integer playerId = 1;
        Mockito.when(scoreRepository.findScoresByPlayerId(1)).thenReturn(
                Arrays.asList(scoreB, scoreA)
        );

        List<PlayerScoreDTO> playerScoreDTOS = playerService.findHistoryById(playerId);

        assertThat(playerScoreDTOS)
                .hasSize(2);

        assertThat(playerScoreDTOS.get(0).getDate()).isBeforeOrEqualTo(playerScoreDTOS.get(1).getDate());



    }
}