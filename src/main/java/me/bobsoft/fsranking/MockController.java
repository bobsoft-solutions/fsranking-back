package me.bobsoft.fsranking;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bobsoft.fsranking.mock.SetMocker;
import me.bobsoft.fsranking.model.CompetitionsSet;
import me.bobsoft.fsranking.model.PlayersSet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/mock")
public class MockController {

    @PostConstruct
    public void mockingSets() {
        SetMocker.mockPlayers();
        SetMocker.mockCompetitions();
    }

    @RequestMapping("/players")
    public String players() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String JSONPlayers = mapper.writeValueAsString(PlayersSet.getPlayers());

        return JSONPlayers;
    }

    @RequestMapping("/competitions")
    public String competitions() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String JSONCompetitions = mapper.writeValueAsString(CompetitionsSet.getCompetitions());

        return JSONCompetitions;
    }

}
