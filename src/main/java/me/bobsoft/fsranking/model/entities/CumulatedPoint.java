package me.bobsoft.fsranking.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cumulated_point")
public class CumulatedPoint implements Comparable<CumulatedPoint> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "points")
    private Integer points;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;

    @Column(name = "place")
    private Integer place;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @Override
    public int compareTo(CumulatedPoint o) {
        return (this.getPoints() - o.getPoints());
    }

    public CumulatedPoint(CumulatedPoint cumulatedPoint) {
        this.idPlayer = cumulatedPoint.getIdPlayer();
        this.points = cumulatedPoint.getPoints();
        this.date = cumulatedPoint.getDate();
        this.place = cumulatedPoint.getPlace();
        this.category = cumulatedPoint.getCategory();
    }

    public CumulatedPoint() {
    }
}
