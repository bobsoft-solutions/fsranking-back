package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "score")
public class Score {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_player")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "id_competition")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "id_default_point")
    private DefaultPoint defaultPoint;

    @Column(name = "score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}