package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.dto.CompetitionDTO;
import me.bobsoft.fsranking.model.entities.*;
import me.bobsoft.fsranking.model.utils.CompetitionWithScoringPlayers;
import me.bobsoft.fsranking.model.utils.ScoreDTO;
import me.bobsoft.fsranking.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompetitionServiceTest {

    private CompetitionRepository competitionRepository;
    private ScoreRepository scoreRepository;
    private PlayerRepository playerRepository;
    private DefaultPointRepository defaultPointRepository;
    private CategoryRepository categoryRepository;
    private CumulatedPointRepository cumulatedPointRepository;

    private CompetitionService competitionService;

    @Before
    public void setUp() {
        competitionRepository = mock(CompetitionRepository.class);
        scoreRepository = mock(ScoreRepository.class);
        playerRepository = mock(PlayerRepository.class);
        defaultPointRepository = mock(DefaultPointRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        cumulatedPointRepository = mock(CumulatedPointRepository.class);

        competitionService = new CompetitionService(
                competitionRepository,
                scoreRepository,
                playerRepository,
                defaultPointRepository,
                categoryRepository,
                cumulatedPointRepository
        );
    }

    @Test
    public void addCompetitionTest() {
        Integer competitionId = 18;
        Integer categoryId = 1;
        Integer playerId = 9;
        Integer defaultPointId = 1;

        Player player = new Player();
        player.setId(playerId);

        Category category = new Category();
        category.setId(categoryId);

        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setImportance(15);

        DefaultPoint defaultPoint = new DefaultPoint();
        defaultPoint.setId(defaultPointId);
        defaultPoint.setValue(22);

        CumulatedPoint cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setPoints(44);
        cumulatedPoint.setIdPlayer(playerId);

        CompetitionWithScoringPlayers competitionWithScoringPlayers = new CompetitionWithScoringPlayers();
        competitionWithScoringPlayers.setCompetition(competition);
        competitionWithScoringPlayers.setCategoryId(categoryId);
        competitionWithScoringPlayers.setScoringPlayersId(Arrays.asList(playerId));

        when(competitionRepository.save(competition)).thenReturn(competition);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(defaultPointRepository.findById(defaultPointId)).thenReturn(Optional.of(defaultPoint));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(cumulatedPointRepository.findFirstByIdPlayerAndCategoryOrderByPointsDesc(playerId, category))
                .thenReturn(Optional.of(cumulatedPoint));
        when(cumulatedPointRepository.getPlayersIdOfCategory(categoryId))
                .thenReturn(Arrays.asList(playerId));
        when(cumulatedPointRepository.findFirstByCategoryIdAndIdPlayerOrderByDateDesc(categoryId, playerId))
                .thenReturn(cumulatedPoint);

        competitionService.addCompetition(competitionWithScoringPlayers);

        cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setPoints(374);
        cumulatedPoint.setIdPlayer(playerId);
        cumulatedPoint.setCategory(category);

        Score score = new Score();
        score.setPlayer(player);
        score.setCompetition(competition);
        score.setDefaultPoint(defaultPoint);
        score.setCategory(category);
        score.setScore(330);

        Mockito.verify(cumulatedPointRepository, Mockito.times(1)).save(cumulatedPoint);
        Mockito.verify(scoreRepository, Mockito.times(1)).save(score);
    }

    @Test
    public void addCompetitionTest2() {
        Integer competitionId = 18;
        Integer categoryId = 1;
        Integer playerId = 9;
        Integer defaultPointId = 1;

        Player player = new Player();
        player.setId(playerId);

        Category category = new Category();
        category.setId(categoryId);

        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setImportance(15);

        DefaultPoint defaultPoint = new DefaultPoint();
        defaultPoint.setId(defaultPointId);
        defaultPoint.setValue(22);

        CumulatedPoint cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setPoints(44);

        CompetitionWithScoringPlayers competitionWithScoringPlayers = new CompetitionWithScoringPlayers();
        competitionWithScoringPlayers.setCompetition(competition);
        competitionWithScoringPlayers.setCategoryId(categoryId);
        competitionWithScoringPlayers.setScoringPlayersId(Arrays.asList(playerId));

        when(competitionRepository.save(competition)).thenReturn(competition);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(defaultPointRepository.findById(defaultPointId)).thenReturn(Optional.of(defaultPoint));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(cumulatedPointRepository.findFirstByIdPlayerAndCategoryOrderByPointsDesc(playerId, category))
                .thenReturn(Optional.of(cumulatedPoint));
        when(cumulatedPointRepository.getPlayersIdOfCategory(categoryId))
                .thenReturn(Arrays.asList(playerId));
        when(cumulatedPointRepository.findFirstByCategoryIdAndIdPlayerOrderByDateDesc(categoryId, playerId))
                .thenReturn(cumulatedPoint);

        competitionService.addCompetition(competitionWithScoringPlayers);

        cumulatedPoint = new CumulatedPoint();
        cumulatedPoint.setPoints(374);
        cumulatedPoint.setIdPlayer(playerId);
        cumulatedPoint.setCategory(category);

        Score score = new Score();
        score.setPlayer(player);
        score.setCompetition(competition);
        score.setDefaultPoint(defaultPoint);
        score.setCategory(category);
        score.setScore(330);

        Mockito.verify(cumulatedPointRepository, Mockito.times(1)).save(cumulatedPoint);
        Mockito.verify(scoreRepository, Mockito.times(1)).save(score);
    }

    @Test
    public void findAll() {
        Integer competitionId = 2;
        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setLocation(new Location());

        Score score = new Score();
        score.setPlayer(new Player());
        score.setCompetition(competition);
        score.setDefaultPoint(new DefaultPoint());
        score.setCategory(new Category());

        when(competitionRepository.findAll()).thenReturn(Arrays.asList(competition));
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "battle"))
                .thenReturn(Arrays.asList(score));
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "challenge"))
                .thenReturn(Arrays.asList());
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "routine"))
                .thenReturn(Arrays.asList());

        List<CompetitionDTO> players = competitionService.findAll();

        assertThat(players.get(0).getClass()).isEqualTo(CompetitionDTO.class);
        assertThat(players.get(0).getBattle().get(0).getClass()).isEqualTo(ScoreDTO.class);

        Mockito.verify(competitionRepository, Mockito.times(1)).findAll();
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "battle");
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "challenge");
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "routine");
    }

    @Test
    public void findByIdTest() {
        Integer competitionId = 7;
        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setLocation(new Location());

        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "battle"))
                .thenReturn(Arrays.asList());
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "challenge"))
                .thenReturn(Arrays.asList());
        when(scoreRepository.findScoresByCompetitionIdAndCategoryName(competitionId, "routine"))
                .thenReturn(Arrays.asList());

        Optional<CompetitionDTO> player = competitionService.findById(competitionId);

        assertThat(player.get().getClass()).isEqualTo(CompetitionDTO.class);

        Mockito.verify(competitionRepository, Mockito.times(2)).findById(competitionId);
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "battle");
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "challenge");
        Mockito.verify(scoreRepository, Mockito.times(1))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "routine");
    }

    @Test
    public void findByIdTest2() {
        Integer competitionId = -7;

        when(competitionRepository.findById(competitionId)).thenReturn(Optional.ofNullable(null));

        Optional<CompetitionDTO> player = competitionService.findById(competitionId);

        assertThat(player.isPresent()).isEqualTo(false);

        Mockito.verify(competitionRepository, Mockito.times(1)).findById(competitionId);
        Mockito.verify(scoreRepository, Mockito.times(0))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "battle");
        Mockito.verify(scoreRepository, Mockito.times(0))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "challenge");
        Mockito.verify(scoreRepository, Mockito.times(0))
                .findScoresByCompetitionIdAndCategoryName(competitionId, "routine");
    }

    @Test
    public void deleteCompetitionTest() {
        Integer competitionId = 7;
        Competition competition = new Competition();
        competition.setId(competitionId);

        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));

        competitionService.deleteCompetition(competitionId);

        Mockito.verify(competitionRepository, Mockito.times(2)).findById(competitionId);
        Mockito.verify(competitionRepository, Mockito.times(1)).delete(competition);
    }

    @Test
    public void deleteCompetitionTest2() {
        Integer competitionId = -7;
        Competition competition = new Competition();
        competition.setId(competitionId);

        when(competitionRepository.findById(competitionId)).thenReturn(Optional.ofNullable(null));

        competitionService.deleteCompetition(competitionId);

        Mockito.verify(competitionRepository, Mockito.times(1)).findById(competitionId);
        Mockito.verify(competitionRepository, Mockito.times(0)).delete(competition);
    }
}