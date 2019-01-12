package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.repository.LocationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationServiceTest {
    
    private LocationRepository locationRepository;

    private LocationService locationService;

    private List<Location> locations = new ArrayList<>();

    @Before
    public void setUp() {
        locationRepository = mock(LocationRepository.class);

        locationService = new LocationService(locationRepository);
        
        Location location = new Location();
        location.setId(1);
        location.setName("Kraków");
        
        locations.add(location);

        location = new Location();
        location.setId(12);
        location.setName("Warszawa");

        locations.add(location);
    }

    @Test
    public void findAllTest1() {
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());

        List<Location> locations = locationService.findAll();

        assertThat(locations).isNotNull();
        assertThat(locations.size()).isEqualTo(0);
    }

    @Test
    public void findAllTest2() {
        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> locations = locationService.findAll();

        assertThat(locations).isNotNull();
        assertThat(locations.size()).isEqualTo(2);
        assertThat(locations).isEqualTo(this.locations);
    }

    @Test
    public void findAllTest3() {
        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> locations = locationService.findAll();

        assertThat(locations).isNotNull();
        assertThat(locations.get(0).getClass()).isEqualTo(Location.class);
    }
}