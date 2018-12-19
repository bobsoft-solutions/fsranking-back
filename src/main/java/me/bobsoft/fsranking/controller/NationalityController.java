package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Nationality;
import me.bobsoft.fsranking.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class NationalityController {

    @Autowired
    private NationalityService nationalityService;

    @GetMapping("/nationalities")
    public List<Nationality> findAll() {
        return nationalityService.findAll();
    }
}
