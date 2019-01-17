package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.repository.CategoryRepository;
import me.bobsoft.fsranking.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    private CategoryService categoryService;

    private CategoryController categoryController;

    private List<Category> categories = new ArrayList<>();

    @Before
    public void setUp() {
        categoryService = mock(CategoryService.class);

        categoryController = new CategoryController(categoryService);

        Category category = new Category();
        category.setId(1);
        category.setName("xyz");

        categories.add(category);

        category = new Category();
        category.setId(2);
        category.setName("category");

        categories.add(category);
    }

    @Test
    public void findAll1() {
        when(categoryService.findAll()).thenReturn(new ArrayList<>());

        List<Category> categories = categoryController.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(0);

        Mockito.verify(categoryService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAll2() {
        when(categoryService.findAll()).thenReturn(categories);

        List<Category> categories = categoryController.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).isEqualTo(this.categories);

        Mockito.verify(categoryService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAll3() {
        when(categoryService.findAll()).thenReturn(categories);

        List<Category> categories = categoryController.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.get(0).getClass()).isEqualTo(Category.class);

        Mockito.verify(categoryService, Mockito.times(1)).findAll();
    }
}