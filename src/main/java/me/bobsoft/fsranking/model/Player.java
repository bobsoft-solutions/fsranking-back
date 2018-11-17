package me.bobsoft.fsranking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "player")
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlayerSocialMedia playerSocialMedia;
    
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
