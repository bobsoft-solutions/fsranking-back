package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.Player;
import me.bobsoft.fsranking.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public Iterable<Player> findAllPlayers() {
        return playerService.findAll();
    }

    @GetMapping("/players/{id}")
    public Optional<Player> findPlayerById(@PathVariable Integer id) {
        return playerService.findById(id);
    }
}
