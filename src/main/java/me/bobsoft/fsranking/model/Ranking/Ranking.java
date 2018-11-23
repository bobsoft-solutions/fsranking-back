package me.bobsoft.fsranking.model.Ranking;

import lombok.Data;
import me.bobsoft.fsranking.model.player.Player;

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
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.points = points;
    }
}
