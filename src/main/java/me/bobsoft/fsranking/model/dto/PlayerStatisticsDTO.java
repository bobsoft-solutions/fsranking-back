package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.utils.PlayerCategoryStatisticsDTO;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;

import java.util.List;

@Data
public class PlayerStatisticsDTO {
    PlayerCategoryStatisticsDTO battle = new PlayerCategoryStatisticsDTO();
    PlayerCategoryStatisticsDTO challenge = new PlayerCategoryStatisticsDTO();
    PlayerCategoryStatisticsDTO routine = new PlayerCategoryStatisticsDTO();
    List<PlayerScoreDTO> history;
}
