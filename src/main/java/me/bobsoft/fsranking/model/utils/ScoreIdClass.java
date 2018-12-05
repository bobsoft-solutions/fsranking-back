package me.bobsoft.fsranking.model.utils;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.model.entities.Competition;
import me.bobsoft.fsranking.model.entities.Player;

import java.io.Serializable;

@Data
public class ScoreIdClass implements Serializable {
    private Player player;
    private Competition competition;
    private Category category;
}
