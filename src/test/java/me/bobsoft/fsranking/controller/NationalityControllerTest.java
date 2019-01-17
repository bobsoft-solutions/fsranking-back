package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Nationality;
import me.bobsoft.fsranking.service.NationalityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NationalityControllerTest {

    private NationalityService nationalityService;

    private NationalityController nationalityController;

    private List<Nationality> nationalities = new ArrayList<>();

    @Before
    public void setUp() {
        nationalityService = mock(NationalityService.class);

        nationalityController = new NationalityController(nationalityService);

        Nationality nationality = new Nationality();
        nationality.setId("POL");
        nationality.setName("Polska");

        nationalities.add(nationality);

        nationality = new Nationality();
        nationality.setId("GER");
        nationality.setName("Germany");

        nationalities.add(nationality);
    }

    @Test
    public void findAllTest1() {
        when(nationalityController.findAll()).thenReturn(new ArrayList<>());

        List<Nationality> nationalities = nationalityController.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.size()).isEqualTo(0);

        Mockito.verify(nationalityService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest2() {
        when(nationalityService.findAll()).thenReturn(nationalities);

        List<Nationality> nationalities = nationalityService.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.size()).isEqualTo(2);
        assertThat(nationalities).isEqualTo(this.nationalities);

        Mockito.verify(nationalityService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest3() {
        when(nationalityService.findAll()).thenReturn(nationalities);

        List<Nationality> nationalities = nationalityService.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.get(0).getClass()).isEqualTo(Nationality.class);

        Mockito.verify(nationalityService, Mockito.times(1)).findAll();
    }
}