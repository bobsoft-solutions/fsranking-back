package me.bobsoft.fsranking.model.utils;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Competition;

import java.util.List;

@Data
public class CompetitionWithScoringPlayers {

    private Competition competition;
    private List<Integer> scoringPlayersId;
    private Integer categoryId;
}
