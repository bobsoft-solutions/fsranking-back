package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.player.Player;
import me.bobsoft.fsranking.repository.PlayerRepository;
import me.bobsoft.fsranking.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Integer id) {
        Optional<Player> optionalPlayerl = playerRepository.findById(id);
        if(!optionalPlayerl.isPresent()) return optionalPlayerl;

        Player player = optionalPlayerl.get();
        player.setSummaryScores(new LinkedHashMap<String, Integer>());

        player.getSummaryScores().put("battle",
                scoreRepository.findByPlayerIdAndCategoryName(id, "battle")
                        .stream()
                        .map(s -> s.getScore())
                        .reduce((s1, s2) -> s1 + s2)
                        .get()
        );

        return Optional.of(player);
    }
}
