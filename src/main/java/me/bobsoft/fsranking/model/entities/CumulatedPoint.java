package me.bobsoft.fsranking.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cumulated_point")
public class CumulatedPoint {
    @Id
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
}
