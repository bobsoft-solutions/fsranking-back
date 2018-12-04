package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.entities.Score;
import me.bobsoft.fsranking.repository.CategoryRepository;
import me.bobsoft.fsranking.repository.CompetitionRepository;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Eval {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    public void eval(String category) {
        //End result
        Map<Integer, List<CumulatedPoint>> cumulatedPointByPlayerId = new LinkedHashMap<>();

        //Get all dates
        List<ZonedDateTime> dates = competitionRepository.findAll().stream()
                                        .map(Competition::getYear)
                                        .distinct()
                                        .sorted()
                                        .collect(Collectors.toList());

        Iterable<Integer> playersIdOfWantedCategory = findPlayersIdFromScoresOfExactCategory("battle");

        for (Integer playerId : playersIdOfWantedCategory) {
            List<Score> scores = scoreRepository.findScoresByPlayerIdAndCategoryName(playerId, "battle");

            int sum = 0;
            List<CumulatedPoint> lcp = new ArrayList<>();

            for(ZonedDateTime date : dates) {
                CumulatedPoint cumulatedPoint = new CumulatedPoint();
                Score score = scores.stream()
                            .filter(s -> s.getCompetition().getYear().equals(date))
                            .findFirst()
                            .orElse(null);

                sum += (score != null) ? score.getScore() : 0;
                Category c = new Category();
                c.setId(1);
                c.setName("battle");

                cumulatedPoint.setCategory(categoryRepository.findByName("battle"));
                cumulatedPoint.setDate(date);
                cumulatedPoint.setIdPlayer(playerId);
                cumulatedPoint.setPlace(-1);
                cumulatedPoint.setPoints(sum);

                lcp.add(cumulatedPoint);
            }
            cumulatedPointByPlayerId.put(playerId, lcp);
        }

        //Adding places
        for(ZonedDateTime date : dates) {
            List<CumulatedPoint> list = cumulatedPointByPlayerId.values()
                                            .stream()
                                            .map(l -> l.stream().filter(cp -> cp.getDate().equals(date)).findFirst().get())
                                            .sorted(Comparator.comparing(CumulatedPoint::getPoints).reversed())
                                            .collect(Collectors.toList());

            for(int i = 0; i < list.size(); i++) {
                list.get(i).setPlace(i + 1);
            }

        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into cumulated_point values\n");

        for (Map.Entry<Integer, List<CumulatedPoint>> entry : cumulatedPointByPlayerId.entrySet()) {
            for(CumulatedPoint cp : entry.getValue()) {
                stringBuilder.append("(" + entry.getKey());
                stringBuilder.append(", " + cp.getPoints());
                stringBuilder.append(", '" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(cp.getDate()) + "'");
                stringBuilder.append(", " + cp.getPlace());
                stringBuilder.append(", " + cp.getCategory().getId() + ")");
                stringBuilder.append(",\n");
            }
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(";");

        try (PrintStream out = new PrintStream(new FileOutputStream("cumulated_point_insert.sql"))) {
            out.print(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Integer> findPlayersIdFromScoresOfExactCategory(String category) {
        List<Integer> playersId = new LinkedList<>();
        System.out.println(scoreRepository);
        for (Score score : scoreRepository.findScoresByCategoryName(category)) {
            if (!playersId.contains(score.getPlayer().getId())) {
                playersId.add(score.getPlayer().getId());
            }
        }
        return playersId;
    }
}
