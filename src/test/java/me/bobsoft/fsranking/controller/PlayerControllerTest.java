package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.dto.PlayerDTO;
import me.bobsoft.fsranking.model.dto.PlayerDTOforPlayersEndpoint;
import me.bobsoft.fsranking.model.entities.Player;
import me.bobsoft.fsranking.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerControllerTest {

    private PlayerService playerService;

    private PlayerController playerController;

    private List<Player> playerList;

    @Before
    public void setUp() throws Exception {
        playerService = mock(PlayerService.class);

        playerController = new PlayerController(playerService);

        playerList = Arrays.asList(
                new Player(),
                new Player()
        );
    }

    @Test
    public void findAllPlayers() {
        List<PlayerDTOforPlayersEndpoint> playerDTOforPlayersEndpointsList =
                playerList.stream()
                        .map(PlayerDTOforPlayersEndpoint::new)
                        .collect(Collectors.toList());

        when(playerService.findAllPlayers()).thenReturn(playerDTOforPlayersEndpointsList);

        List<PlayerDTOforPlayersEndpoint> playerDTOforPlayersEndpoints = playerController.findAllPlayers();

        assertThat(playerDTOforPlayersEndpoints).isEqualTo(playerDTOforPlayersEndpointsList);

        Mockito.verify(playerService, Mockito.times(1)).findAllPlayers();
    }

    @Test
    public void postPlayer() {
        Player player = playerList.get(0);
        PlayerDTO playerDTO = new PlayerDTO(
                player,
                new HashMap<String, Integer>(),
                new HashMap<String, Integer>()
        );

        when(playerController.postPlayer(player)).thenReturn(playerDTO);

        PlayerDTO returnedPlayerDTO = playerController.postPlayer(player);

        assertThat(returnedPlayerDTO).isEqualTo(playerDTO);

        Mockito.verify(playerService, Mockito.times(1)).postPlayer(player);
    }

    @Test
    public void findPlayerById() {
        Integer playerId = 18;

        Player player = new Player();
        player.setId(playerId);

        PlayerDTO playerDTO = new PlayerDTO(
                player,
                new HashMap<String, Integer>(),
                new HashMap<String, Integer>()
        );

        when(playerService.findById(playerId)).thenReturn(playerDTO);

        PlayerDTO returnedPlayerDTO = playerController.findPlayerById(playerId);

        assertThat(returnedPlayerDTO).isEqualTo(playerDTO);

        Mockito.verify(playerService, Mockito.times(1)).findById(playerId);
    }

    @Test
    public void findPlayerById2() {
        Integer playerId = 99;

        PlayerDTO playerDTO = null;

        when(playerService.findById(playerId)).thenReturn(playerDTO);

        PlayerDTO returnedPlayerDTO = playerController.findPlayerById(playerId);

        assertThat(returnedPlayerDTO).isEqualTo(playerDTO);

        Mockito.verify(playerService, Mockito.times(1)).findById(playerId);
    }

    @Test
    public void deletePlayer() {
    }

    @Test
    public void findPlayerStatisticsById() {
    }
}