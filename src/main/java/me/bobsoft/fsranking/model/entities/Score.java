package me.bobsoft.fsranking.model.entities;

import lombok.Data;
import me.bobsoft.fsranking.model.utils.ScoreIdClass;

import javax.persistence.*;

@Data
@Entity
@Table(name = "score")
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

    @ManyToOne
    @JoinColumn(name = "id_default_point")
    private DefaultPoint defaultPoint;

    @Column(name = "score")
    private Integer score;
    
    @ManyToOne
    @JoinColumn(name = "id_category")
    @Id
    private Category category;
}