package me.bobsoft.fsranking.model.utils;

import lombok.Data;

@Data
public class PlayerStatistics {
    PlayerCategoryStatistics battle = new PlayerCategoryStatistics();
    PlayerCategoryStatistics challenge = new PlayerCategoryStatistics();
    PlayerCategoryStatistics routine = new PlayerCategoryStatistics();
}
