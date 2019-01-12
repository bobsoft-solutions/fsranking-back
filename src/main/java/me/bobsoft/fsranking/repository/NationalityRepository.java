package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, String> {
}
