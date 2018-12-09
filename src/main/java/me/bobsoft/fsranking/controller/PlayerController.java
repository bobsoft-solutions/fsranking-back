package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.model.utils.PlayerHistory;
import me.bobsoft.fsranking.model.utils.PlayerStatistics;
import me.bobsoft.fsranking.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public Iterable<Player> findAllPlayers() { return playerService.findAll(); }

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

    @GetMapping("/players/{id}/statistics")
    public PlayerStatistics findPlayerStatisticsById(@PathVariable Integer id) {
        return playerService.findStatisticsById(id);
    }

    @GetMapping("/players/{id}/history")
    public List<PlayerHistory> findPlayerHistoryById(@PathVariable Integer id) {
        return playerService.findHistoryById(id);
    }
}
