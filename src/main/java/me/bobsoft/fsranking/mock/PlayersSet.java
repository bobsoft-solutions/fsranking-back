package me.bobsoft.fsranking.mock;

import lombok.Getter;
import me.bobsoft.fsranking.model.Player;

import java.util.HashSet;
import java.util.Set;

public class PlayersSet {

    @Getter
    private static Set<Player> players = new HashSet<>();

    public static void addPlayer(Player player) {
        players.add(player);
    }

    private PlayersSet() {}

}
