package me.bobsoft.fsranking.model.player;

import lombok.Data;
import me.bobsoft.fsranking.model.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
    private Date date;

    @Column(name = "place")
    private Integer place;

//    @Column(name = "id_category")
//    private Category category;
}
