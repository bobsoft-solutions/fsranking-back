package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.dto.PlayerDTOforPlayersEndpoint;
import me.bobsoft.fsranking.model.dto.PlayerStatisticsDTO;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.utils.PlayerPredictions;
import me.bobsoft.fsranking.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<PlayerDTOforPlayersEndpoint> findAllPlayers() {
        return playerService.findAllPlayers();
    }

    @PostMapping("/players")
    public PlayerDTO postPlayer(@RequestBody Player player) {
        return playerService.postPlayer(player);
    }

    @GetMapping("/players/{id}")
    public PlayerDTO findPlayerById(@PathVariable Integer id) {
        return playerService.findById(id);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayer(id);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> putPlayer(@PathVariable Integer id, @RequestBody Player player) {
        return playerService.putPlayer(id, player);
    }

    @GetMapping("/players/{id}/statistics")
    public PlayerStatisticsDTO findPlayerStatisticsById(@PathVariable Integer id) {
        return playerService.findStatisticsById(id);
    }

    @GetMapping("/players/{id}/predictions")
    public PlayerPredictions findPlayerPredictionsById(@PathVariable Integer id) {
        return playerService.findPlayerPredictionsById(id);
    }
}
