package me.bobsoft.fsranking.model.player;

import lombok.Data;
import me.bobsoft.fsranking.model.points.ChallengePoint;

import java.util.List;

@Data
public class PlayerPodiumCount {
    private Integer countOf1st;
    private Integer countOf2nd;
    private Integer countOf3rd;

    private Iterable<ChallengePoint> points;
}
