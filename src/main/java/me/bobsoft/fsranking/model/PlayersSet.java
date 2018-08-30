package me.bobsoft.fsranking.model;

import lombok.Getter;

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
