package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Nationality;
import me.bobsoft.fsranking.repository.NationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalityService {

    @Autowired
    private NationalityRepository nationalityRepository;

    public List<Nationality> findAll() {
        return nationalityRepository.findAll();
    }
}
