package me.bobsoft.fsranking;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bobsoft.fsranking.mock.SetMocker;
import me.bobsoft.fsranking.model.PlayersSet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/mock")
public class MockController {

    @PostConstruct
    public void mockingPlayers() {
        SetMocker.mockPlayers();
    }

    @RequestMapping("/players")
    public String players() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String JSONPlayers = mapper.writeValueAsString(PlayersSet.getPlayers());

        return JSONPlayers;
    }

}
