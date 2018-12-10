package me.bobsoft.fsranking.model.dto;

import lombok.Data;
import me.bobsoft.fsranking.model.entities.CumulatedPoint;
import me.bobsoft.fsranking.model.utils.PlayerScoreDTO;

import java.util.List;

@Data
public class PlayerStatisticsDTO {
    List<CumulatedPoint> battleCumulatedPoint;
    List<CumulatedPoint> challengeCumulatedPoint;
    List<CumulatedPoint> routineCumulatedPoint;
    List<PlayerScoreDTO> history;
}
