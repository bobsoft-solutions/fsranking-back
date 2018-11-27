package me.bobsoft.fsranking.model.entities;

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
    private SocialMedia socialMedia;
}