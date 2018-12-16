package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.entities.SocialMedia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
public class PlayerDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nick;
    private String nationality;
    private Integer age;
    private SocialMedia socialMedia;
    private Map<String, Integer> summaryScores;
    private Map<String, Integer> positions;

    public PlayerDTO(Player player, Map<String, Integer> summaryScores, Map<String, Integer> positions) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nick = player.getNick();
        this.nationality = player.getNationality();
        this.age = player.getBirthYear();

        LocalDate now = LocalDate.now();
        String currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));

        this.age = player.getBirthYear() == null ?
                null : Integer.parseInt(currentYear) - player.getBirthYear();

        this.socialMedia = player.getSocialMedia();
        this.summaryScores = summaryScores;
        this.positions = positions;
    }
}
