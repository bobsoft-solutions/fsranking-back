package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.Category;
import me.bobsoft.fsranking.model.player.CumulatedPoint;
import me.bobsoft.fsranking.model.score.Score;
import me.bobsoft.fsranking.repository.CategoryRepository;
import me.bobsoft.fsranking.repository.CumulatedPointRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class Eval {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CumulatedPointRepository cumulatedPointRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void eval(String category) {
//        cumulatedPointRepository.deleteAll();
        Iterable<Integer> playersIdOfWantedCategory = findPlayersIdFromScoresOfExactCategory("battle");

        for (Integer playerId : playersIdOfWantedCategory) {
            List<Score> scores = scoreRepository.findByPlayerIdAndCategoryName(playerId, "battle");
            Collections.sort(scores, (Score s1, Score s2) -> {
                return s1.getCompetition().getYear().compareTo(s2.getCompetition().getYear());
            });

            int sum = 0;
            List<CumulatedPoint> lcp = new ArrayList<>();

            for(Score s : scores) {
                if(playerId == 2 && s.getScore() == 75) {
                    continue;
                }
                CumulatedPoint cumulatedPoint = new CumulatedPoint();
                sum += s.getScore();
                cumulatedPoint.setCategory(categoryRepository.findById(1).get());
                cumulatedPoint.setDate(s.getCompetition().getYear());
                cumulatedPoint.setIdPlayer(playerId);
                cumulatedPoint.setPlace(-1);
                cumulatedPoint.setPoints(sum);
                cumulatedPointRepository.saveAndFlush(cumulatedPoint);
                System.out.println("ID: " + playerId + "; sum: " + sum);
            }
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
