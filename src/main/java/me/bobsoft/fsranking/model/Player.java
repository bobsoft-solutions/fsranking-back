package me.bobsoft.fsranking.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "photo")
    private String photo;

    @Column(name = "age")
    private int age;

    @Column(name = "points")
    private int points;

    // to satisfy Mocker
    @Builder
    public Player(Integer id, String firstName, String lastName, String nick, String nationality, String photo, int age, int points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.nationality = nationality;
        this.photo = photo;
        this.age = age;
        this.points = points;
    }
}
