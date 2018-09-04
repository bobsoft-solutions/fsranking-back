package me.bobsoft.fsranking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "player", schema = "public")
public class Player {

    @Id
    @Column(name = "id_player")
    private Integer id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick")
    private String nick;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "photo")
    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "social_media")
    private PlayerSocialMedia playerSocialMedia;

    @Column(name = "age")
    private int age;

    @Column(name = "point")
    private int points;

    // to satisfy Mocker
    @Builder
    public Player(Integer id, String firstName, String lastName, String nick, String nationality, String photo, PlayerSocialMedia playerSocialMedia, int age, int points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.nationality = nationality;
        this.photo = photo;
        this.playerSocialMedia = playerSocialMedia;
        this.age = age;
        this.points = points;
    }
}
