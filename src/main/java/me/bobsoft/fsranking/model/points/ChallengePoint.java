package me.bobsoft.fsranking.model.points;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "challenge_point")
@Data
@Entity
public class ChallengePoint {
    @Id
    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "points")
    private Integer points;

    @Column(name = "date")
    private Date date;

    @Column(name = "place")
    private Integer place;
}
