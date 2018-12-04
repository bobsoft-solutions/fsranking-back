package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }
}
