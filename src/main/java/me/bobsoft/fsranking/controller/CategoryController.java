package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> findAll() {
        return categoryService.findAll();
    }
}
