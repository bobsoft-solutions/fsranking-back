package me.bobsoft.fsranking.model.Ranking;

import lombok.Data;
import me.bobsoft.fsranking.model.player.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Ranking {

    private Integer idPlayer;
    private String firstName;
    private String lastName;
    private String nick;
    private Integer age;
    private String nationality;
    private int points;

    public Ranking(Player player, Integer points) {
        this.idPlayer = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nick = player.getNick();

        LocalDate now = LocalDate.now();
        String currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));

        this.age = player.getAge() == null ?
                null : Integer.parseInt(currentYear) - player.getAge();

        this.nationality = player.getNationality();
        this.points = points;
    }
}
