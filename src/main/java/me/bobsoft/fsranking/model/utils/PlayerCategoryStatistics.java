package me.bobsoft.fsranking.model.utils;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;

@Data
public class PlayerCategoryStatistics {
    private Integer countOf1st;
    private Integer countOf2nd;
    private Integer countOf3rd;

    private Iterable<CumulatedPoint> points;
}
