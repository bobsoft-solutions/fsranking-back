package me.bobsoft.fsranking.model.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;

import java.util.Date;

@Data
public class CumulatedPointDTO {
    private Integer place;

    private Integer points;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    public CumulatedPointDTO(CumulatedPoint cumulatedPoint) {
        this.place = cumulatedPoint.getPlace();
        this.points = cumulatedPoint.getPoints();
        this.date = cumulatedPoint.getDate();
    }
}
