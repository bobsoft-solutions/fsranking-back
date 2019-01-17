package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.SocialMedia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class PlayerDTOforPlayersEndpoint {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nick;
    private String nationality;
    private Integer age;
    private SocialMedia socialMedia;

    public PlayerDTOforPlayersEndpoint(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nick = player.getNick();
        this.nationality = player.getNationality();

        LocalDate now = LocalDate.now();
        String currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));
        this.age = player.getBirthYear() == null ?
                null : Integer.parseInt(currentYear) - player.getBirthYear();

        this.socialMedia = player.getSocialMedia();
    }
}
