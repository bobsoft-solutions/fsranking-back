package me.bobsoft.fsranking.model.score;

import lombok.Data;
import me.bobsoft.fsranking.model.Category;
import me.bobsoft.fsranking.model.Competition;
import me.bobsoft.fsranking.model.player.Player;

import java.io.Serializable;

@Data
public class ScoreIdClass implements Serializable {
    private Player player;
    private Competition competition;
    private Category category;
}
