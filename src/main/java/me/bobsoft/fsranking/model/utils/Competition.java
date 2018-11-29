package me.bobsoft.fsranking.model.utils;

import lombok.Data;

@Data
public class Competition {
    private Integer id;
    private String name;

    public Competition(me.bobsoft.fsranking.model.entities.Competition competition) {
        this.id = competition.getId();
        this.name = competition.getName();
    }
}
