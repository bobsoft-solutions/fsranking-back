package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.PlayerSocialMedia;
import me.bobsoft.fsranking.repository.PlayerSocialMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerSocialMediaService {

    @Autowired
    private PlayerSocialMediaRepository playerSocialMediaRepository;

    public List<PlayerSocialMedia> findAll() {
        return playerSocialMediaRepository.findAll();
    }
}
