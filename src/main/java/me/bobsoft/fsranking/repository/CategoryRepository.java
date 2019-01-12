package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String categoryName);
}
