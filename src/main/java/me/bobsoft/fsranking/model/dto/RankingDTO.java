package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.utils.Trend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class RankingDTO implements Comparable<RankingDTO> {

    private Integer idPlayer;
    private String firstName;
    private String lastName;
    private String nick;
    private Integer age;
    private String nationality;
    private int points;
    private Trend trend;
    private Integer position;

    public RankingDTO(Player player, Integer points, Trend trend, Integer position) {
        this.idPlayer = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nick = player.getNick();

        LocalDate now = LocalDate.now();
        String currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));

        this.age = player.getBirthYear() == null ?
                null : Integer.parseInt(currentYear) - player.getBirthYear();

        this.nationality = player.getNationality();
        this.points = points;
        this.trend = trend;
        this.position = position;
    }

    @Override
    public int compareTo(RankingDTO r) {
        return this.getPoints() - r.getPoints();
    }
}
