package me.bobsoft.fsranking.model.utils;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;

@Data
public class PlayerCategoryStatisticsDTO {
    private Integer place1Count;
    private Integer place2Count;
    private Integer place3Count;

    private Iterable<CumulatedPoint> cumulatedPoints;
}
