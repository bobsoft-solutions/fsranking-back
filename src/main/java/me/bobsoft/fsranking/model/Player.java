package me.bobsoft.fsranking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private int id;
    private String firstName;
    private String lastName;
    private String nick;
    private String nationality;
    private String photo;
    private SocialMedia socialMedia;
    private int age;
    private int points;

    // TODO: Player builder, id and nickname required (is it really needed?)

    public Player(int id, String firstName, String lastName, String nick, String nationality, String photo, SocialMedia socialMedia, int age, int points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.nationality = nationality;
        this.photo = photo;
        this.socialMedia = socialMedia;
        this.age = age;
        this.points = points;
    }
}
