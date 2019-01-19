package me.bobsoft.fsranking.model.utils;

import lombok.Data;

import java.util.Map;

@Data
public class PlayerPredictions {

    private Integer playerId;
    private Map<String, Integer> groupNamesAndPredictions;

    public PlayerPredictions(Integer playerId, Map<String, Integer> groupNamesAndPredictions) {
        this.playerId = playerId;
        this.groupNamesAndPredictions = groupNamesAndPredictions;
    }
}
