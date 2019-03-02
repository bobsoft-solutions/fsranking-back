package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @PostMapping("/locations")
    public ResponseEntity addLocations(@RequestBody Location location) {
        return locationService.postLocation(location);
    }
}
