package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Nationality;
import me.bobsoft.fsranking.repository.NationalityRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NationalityServiceTest {

    private NationalityRepository nationalityRepository;

    private NationalityService nationalityService;

    private List<Nationality> nationalities = new ArrayList<>();

    @Before
    public void setUp() {
        nationalityRepository = mock(NationalityRepository.class);

        nationalityService = new NationalityService(nationalityRepository);

        Nationality nationality = new Nationality();
        nationality.setId("POL");
        nationality.setName("Poland");

        nationalities.add(nationality);

        nationality = new Nationality();
        nationality.setId("GER");
        nationality.setName("Germany");

        nationalities.add(nationality);
    }

    @Test
    public void findAllTest1() {
        when(nationalityRepository.findAll()).thenReturn(new ArrayList<>());

        List<Nationality> nationalities = nationalityService.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.size()).isEqualTo(0);
    }

    @Test
    public void findAllTest2() {
        when(nationalityRepository.findAll()).thenReturn(nationalities);

        List<Nationality> nationalities = nationalityService.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.size()).isEqualTo(2);
        assertThat(nationalities).isEqualTo(this.nationalities);
    }

    @Test
    public void findAllTest3() {
        when(nationalityRepository.findAll()).thenReturn(nationalities);

        List<Nationality> nationalities = nationalityService.findAll();

        assertThat(nationalities).isNotNull();
        assertThat(nationalities.get(0).getClass()).isEqualTo(Nationality.class);
    }
}