package me.bobsoft.fsranking.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class Competition {

    private int id;
    private String name;
    private Location location;
    private ZonedDateTime year;
    private int importance;

    public Competition(int id, String name, Location location, ZonedDateTime year, int importance) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.year = year;
        this.importance = importance;
    }
}
