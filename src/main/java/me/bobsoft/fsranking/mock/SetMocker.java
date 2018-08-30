package me.bobsoft.fsranking.mock;

import me.bobsoft.fsranking.model.*;

import java.time.ZonedDateTime;

public class SetMocker {

    private SetMocker() {}

    private static final int NUMBER_OF_PLAYERS = 100;
    private static final int NUMBER_OF_COMPETITIONS = 100;

    public static void mockPlayers() {

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {

            int id = i;
            String firstName = "firstName" + i;
            String lastName = "lastName" + i;
            String nick = "nick" + i;
            String nationality = "POL" + i;
            String photo = "photoURL" + i;
            PlayerSocialMedia playerSocialMedia = null;
            int age = 10 + i;
            int points = 100 + i;

            Player player = new Player(id, firstName, lastName, nick, nationality, photo, playerSocialMedia,  age, points);

            PlayersSet.addPlayer(player);
        }
    }

    public static void mockCompetitions() {

        for (int i = 0; i < NUMBER_OF_COMPETITIONS; i++) {

            int id = i;
            String name = "firstName" + i;
            Location location = null;
            ZonedDateTime year = null;
            int importance = 20 + i;

            Competition competition = new Competition(id, name, location, year, importance);

            CompetitionsSet.addCompetition(competition);
        }
    }
}
