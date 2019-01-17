package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    
    @Column(name = "birth_year")
    private Integer birthYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private SocialMedia socialMedia;

    public void updateWhenNotNull(Player other) {
        if(other == null) return;

        String firstName = other.getFirstName();
        this.firstName = firstName == null ? this.firstName : firstName;

        String lastName = other.getLastName();
        this.lastName = lastName == null ? this.lastName : lastName;

        String nationality = other.getNationality();
        this.nationality = nationality == null ? this.nationality : nationality;

        String nick = other.getNick();
        this.nick = nick == null ? this.nick : nick;

        Integer birthYear = other.getBirthYear();
        this.birthYear = birthYear == null ? this.birthYear : birthYear;

        if(this.socialMedia != null)
            this.socialMedia.updateWhenNotNull(other.getSocialMedia());
    }
}
