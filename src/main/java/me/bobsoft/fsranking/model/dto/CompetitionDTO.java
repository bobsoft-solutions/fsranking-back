package me.bobsoft.fsranking.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.utils.ScoreDTO;

import java.util.Date;
import java.util.List;

@Data
public class CompetitionDTO {
    private Integer id;
    private String name;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Integer importance;

    // Set in service
    private List<ScoreDTO> battle;
    private List<ScoreDTO> challenge;
    private List<ScoreDTO> routine;

    public CompetitionDTO(Competition competition) {
        this.id = competition.getId();
        this.name = competition.getName();
        this.location = competition.getLocation().getName();
        this.date = competition.getDate();
        this.importance = competition.getImportance();
    }
}
