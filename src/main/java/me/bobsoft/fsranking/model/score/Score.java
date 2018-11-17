package me.bobsoft.fsranking.model.score;

import lombok.Data;
import me.bobsoft.fsranking.model.Category;
import me.bobsoft.fsranking.model.Competition;
import me.bobsoft.fsranking.model.player.Player;

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

    @Column(name = "score")
    private Integer score;

    @Column(name = "place")
    private Integer place;

    @ManyToOne
    @JoinColumn(name = "id_category")
    @Id
    private Category category;
}
