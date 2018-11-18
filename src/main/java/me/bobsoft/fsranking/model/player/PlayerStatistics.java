package me.bobsoft.fsranking.model.player;

import lombok.Data;

@Data
public class PlayerStatistics {
    PlayerPodiumCount battle = new PlayerPodiumCount();
    PlayerPodiumCount challenge = new PlayerPodiumCount();
    PlayerPodiumCount routine = new PlayerPodiumCount();
}
