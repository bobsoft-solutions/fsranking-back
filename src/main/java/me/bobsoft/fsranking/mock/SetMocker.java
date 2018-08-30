package me.bobsoft.fsranking.mock;

import me.bobsoft.fsranking.model.Player;
import me.bobsoft.fsranking.model.PlayersSet;

public class SetMocker {

    private static final int NUMBER_OF_PLAYERS = 100;

    public static void mockPlayers() {

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {

            int id = i;
            String firstName = "firstName" + i;
            String lastName = "lastName" + i;
            String nick = "nick" + i;
            String nationality = "POL" + i;
            String photo = "photoURL" + i;

            int age = 10 + i;
            int points = 100 + i;

            Player player = new Player(id, firstName, lastName, nick, nationality, photo, null,  age, points);

            PlayersSet.addPlayer(player);
        }
    }

    private SetMocker() {
    }
}
