package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.PlayerSocialMedia;
import me.bobsoft.fsranking.service.PlayerSocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerSocialMediaController {

    @Autowired
    private PlayerSocialMediaService playerSocialMediaService;

    @CrossOrigin
    @GetMapping("/playerSocialMedia")
    public List<PlayerSocialMedia> findAllPlayerSocialMedia() {
        return playerSocialMediaService.findAll();
    }
}
