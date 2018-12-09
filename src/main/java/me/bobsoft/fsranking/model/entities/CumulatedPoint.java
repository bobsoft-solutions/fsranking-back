package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cumulated_point")
public class CumulatedPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "points")
    private Integer points;

    @Column(name = "date")
    private Date date;

    @Column(name = "place")
    private Integer place;

    @Transient
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
