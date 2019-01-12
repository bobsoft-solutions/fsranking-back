package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.RankingDTO;
import me.bobsoft.fsranking.model.entities.*;
import me.bobsoft.fsranking.model.utils.Trend;
import me.bobsoft.fsranking.repository.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RankingServiceTest {

    private ScoreRepository scoreRepository;
    private PlayerRepository playerRepository;
    private CumulatedPointRepository cumulatedPointRepository;
    private CategoryRepository categoryRepository;

    private RankingService rankingService;

    private List<CumulatedPoint> cumulatedPointList = new ArrayList<>();

    @Before
    public void setUp() {
        scoreRepository = mock(ScoreRepository.class);
        playerRepository = mock(PlayerRepository.class);
        cumulatedPointRepository = mock(CumulatedPointRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        rankingService = new RankingService(scoreRepository,
                                            playerRepository,
                                            cumulatedPointRepository,
                                            categoryRepository);
        Player player = new Player();
        player.setId(13);
        player.setFirstName("Franz");
        player.setLastName("Smuda");
        player.setBirthYear(1970);
        player.setNationality("POL");

        Category category = new Category();
        category.setName("battle");
        category.setId(1);

        List<Score> scoreList = new ArrayList<>();
        Score score = new Score();
        score.setPlayer(player);
        score.setCategory(category);
        score.setScore(25);
        scoreList.add(score);

        score = new Score();
        score.setPlayer(player);
        score.setCategory(category);
        score.setScore(55);
        scoreList.add(score);

        CumulatedPoint cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setIdPlayer(player.getId());
        cumulatedPoint.setCategory(category);
        try {
            cumulatedPoint.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2010-12-11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cumulatedPointList.add(cumulatedPoint);

        cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setIdPlayer(player.getId());
        cumulatedPoint.setCategory(category);
        try {
            cumulatedPoint.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cumulatedPointList.add(cumulatedPoint);

        when(playerRepository.findById(13)).thenReturn(Optional.of(player));
        when(categoryRepository.findByName("battle")).thenReturn(category);
        when(scoreRepository.findScoresByPlayerId(13)).thenReturn(scoreList);
        when(scoreRepository.findScoresByCategoryName("battle")).thenReturn(scoreList);
        when(cumulatedPointRepository.findAllByIdPlayerAndCategoryId(13, category.getId()))
                .thenReturn(cumulatedPointList);
    }


    @Test
    public void findPlayerAndSummaryScoreTest() {
        cumulatedPointList.get(0).setPlace(5);
        cumulatedPointList.get(1).setPlace(2);

        List<RankingDTO> result = rankingService.findPlayerAndSummaryScore("battle");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTrend()).isEqualTo(Trend.UP);
    }

    @Test
    public void findPlayerAndSummaryScoreTest2() {
        cumulatedPointList.get(0).setPlace(5);
        cumulatedPointList.get(1).setPlace(5);

        List<RankingDTO> result = rankingService.findPlayerAndSummaryScore("battle");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTrend()).isEqualTo(Trend.SAME);
    }

    @Test
    public void findPlayerAndSummaryScoreTest3() {
        cumulatedPointList.get(0).setPlace(3);
        cumulatedPointList.get(1).setPlace(5);

        List<RankingDTO> result = rankingService.findPlayerAndSummaryScore("battle");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTrend()).isEqualTo(Trend.DOWN);
    }

    @Test
    public void findPlayerAndSummaryScoreTest4() {
        List<CumulatedPoint> localCumulatedPointList = new ArrayList<>(cumulatedPointList);
        localCumulatedPointList.remove(1);

        when(cumulatedPointRepository.findAllByIdPlayerAndCategoryId(13, 1))
                .thenReturn(localCumulatedPointList);

        List<RankingDTO> result = rankingService.findPlayerAndSummaryScore("battle");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTrend()).isEqualTo(Trend.SAME);
    }

    @Test
    public void findPlayerAndSummaryScoreTest5() {
        cumulatedPointList.get(0).setPlace(5);
        cumulatedPointList.get(1).setPlace(5);
        List<RankingDTO> result = rankingService.findPlayerAndSummaryScore("battle");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getPoints()).isEqualTo(80);
    }
}
