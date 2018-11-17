package me.bobsoft.fsranking.model.player;

import lombok.Builder;
import lombok.Data;
import me.bobsoft.fsranking.model.player.PlayerSocialMedia;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

@Data
@Entity
@Table(name = "player", schema = "public")
public class Player {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick")
    private String nick;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "birth_date")
    private Integer age;

    @OneToOne
    @JoinColumn(name = "id")
    private PlayerSocialMedia playerSocialMedia;

    //optional for /players/{id} endpoint
    @Transient
    private Map<String, Integer> summaryScores;

    @Builder
    public Player(Integer id, String firstName, String lastName, String nick, String nationality, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.nationality = nationality;
        this.age = age;
    }
}
