package me.bobsoft.fsranking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "score", schema = "public")
@IdClass(ScoreIdClass.class)
public class Score {

    @ManyToOne
    @JoinColumn(name = "id_player")
    @Id
    private Player player;

    @ManyToOne
    @JoinColumn(name = "id_competition")
    @Id
    private Competition competition;

    @Column(name = "score")
    private Integer lastName;

    @Column(name = "place")
    private Integer place;

    @ManyToOne
    @JoinColumn(name = "id_category")
    @Id
    private Category category;
}

