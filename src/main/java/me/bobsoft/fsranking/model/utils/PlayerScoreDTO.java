package me.bobsoft.fsranking.model.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.bobsoft.fsranking.model.entities.Score;

import java.util.Date;

@Data
public class PlayerScoreDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Integer place;
    private Integer score;
    private String category;
    private Competition competition;

    public PlayerScoreDTO(Score score) {
        this.date = score.getCompetition().getDate();
        this.place = score.getDefaultPoint().getId();
        this.category = score.getCategory().getName();
        this.score = score.getScore();
        this.competition = new Competition(score.getCompetition());
    }
}
