package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public ResponseEntity postLocation(Location location) {

        if (!locationRepository.findByName(location.getName()).isPresent()) {
            locationRepository.save(location);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }
}
