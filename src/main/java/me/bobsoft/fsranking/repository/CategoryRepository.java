package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.Category;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Integer> {
    public Category findByName(String name);
}
