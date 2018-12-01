package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.User;
import me.bobsoft.fsranking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/secure/admins")
    public List<User> findMe() {
        return userRepository.findAll();
    }
}
