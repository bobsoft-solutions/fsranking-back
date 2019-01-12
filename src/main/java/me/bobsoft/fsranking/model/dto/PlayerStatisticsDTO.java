package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.utils.CumulatedPointDTO;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;

import java.util.List;

@Data
public class PlayerStatisticsDTO {
    List<CumulatedPointDTO> battleCumulatedPoint;
    List<CumulatedPointDTO> challengeCumulatedPoint;
    List<CumulatedPointDTO> routineCumulatedPoint;
    List<PlayerScoreDTO> scoreHistory;
}
