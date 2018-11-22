package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingCategory {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking/battle")
    public Iterable<Integer> getPlayersOfBattleCategory() {
        return rankingService.findPlayerAndSummaryScore("battle");
    }
}
