package me.bobsoft.fsranking.model.Ranking;

import lombok.Data;
import me.bobsoft.fsranking.model.player.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Ranking {

    private Integer idPlayer;
    private String firstName;
    private String lastName;
    private String nick;
    private Integer age;
    private String nationality;
    private Integer points;

    public Ranking(Player player, Integer points) {
        this.idPlayer = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nick = player.getNick();
        this.age = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())) - player.getAge();
        this.nationality = player.getNationality();
        this.points = points;
    }
}
