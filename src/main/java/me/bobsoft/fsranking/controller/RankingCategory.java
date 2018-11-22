package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingCategory {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking/{category}")
    public Iterable<Integer> getPlayersOfCategory(@PathVariable String category) {
        return rankingService.findPlayerAndSummaryScore(category);
    }
}
