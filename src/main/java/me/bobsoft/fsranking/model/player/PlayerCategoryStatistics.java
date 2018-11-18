package me.bobsoft.fsranking.model.player;

import lombok.Data;

@Data
public class PlayerCategoryStatistics {
    private Integer countOf1st;
    private Integer countOf2nd;
    private Integer countOf3rd;

    private Iterable<CumulatedPoint> points;
}
