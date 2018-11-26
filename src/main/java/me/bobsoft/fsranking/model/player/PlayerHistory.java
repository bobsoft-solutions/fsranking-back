package me.bobsoft.fsranking.model.player;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.bobsoft.fsranking.model.score.Score;

import java.time.ZonedDateTime;

@Data
public class PlayerHistory {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private ZonedDateTime date;
    private String competitionName;
    private Integer place;
    private String category;

    public PlayerHistory(Score score) {
        this.date = score.getCompetition().getYear();
        this.competitionName = score.getCompetition().getName();
        this.place = score.getDefaultPoint().getId();
        this.category = score.getCategory().getName();
    }
}
