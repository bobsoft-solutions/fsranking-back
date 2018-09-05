package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
}