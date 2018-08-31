package me.bobsoft.fsranking.mock;

import lombok.Getter;
import me.bobsoft.fsranking.model.Competition;

import java.util.HashSet;
import java.util.Set;

public class CompetitionsSet {

    @Getter
    private static Set<Competition> competitions = new HashSet<>();

    public static void addCompetition(Competition competition) {
        competitions.add(competition);
    }

    private CompetitionsSet() {}
}
