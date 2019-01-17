package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Location;
import me.bobsoft.fsranking.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationControllerTest {

    private LocationService locationService;

    private LocationController locationController;

    private List<Location> locations = new ArrayList<>();

    @Before
    public void setUp() {
        locationService = mock(LocationService.class);

        locationController = new LocationController(locationService);

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
        when(locationService.findAll()).thenReturn(new ArrayList<>());

        List<Location> locations = locationController.findAll();

        assertThat(locations).isNotNull();
        assertThat(locations.size()).isEqualTo(0);

        Mockito.verify(locationService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest2() {
        when(locationService.findAll()).thenReturn(locations);

        List<Location> locations = locationController.findAll();

        assertThat(locations.size()).isEqualTo(2);
        assertThat(locations).isEqualTo(this.locations);

        Mockito.verify(locationService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest3() {
        when(locationService.findAll()).thenReturn(locations);

        List<Location> locations = locationController.findAll();

        assertThat(locations).isNotNull();
        assertThat(locations.get(0).getClass()).isEqualTo(Location.class);

        Mockito.verify(locationService, Mockito.times(1)).findAll();
    }
}