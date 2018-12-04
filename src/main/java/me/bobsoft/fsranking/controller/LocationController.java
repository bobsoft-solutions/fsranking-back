package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public Iterable<Location> findAll() {
        return locationService.findAll();
    }
}
