package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Score;

@Data
public class ScoreDTO {
    private Integer player;
    private Integer competition;
    private Integer place;
    private Integer score;
    private String category;

    public ScoreDTO(Score score) {
        this.player = score.getPlayer().getId();
        this.competition = score.getCompetition().getId();
        this.place = score.getDefaultPoint().getId();
        this.score = score.getScore();
        this.category = score.getCategory().getName();
    }
}
