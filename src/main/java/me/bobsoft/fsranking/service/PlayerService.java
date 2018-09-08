package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.Player;
import me.bobsoft.fsranking.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Integer id) {
        return playerRepository.findById(id);
    }
}
