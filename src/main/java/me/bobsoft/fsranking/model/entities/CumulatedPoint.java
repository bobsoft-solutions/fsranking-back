package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "cumulated_point")
public class CumulatedPoint {
    @Id
    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "points")
    private Integer points;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "place")
    private Integer place;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
