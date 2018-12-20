package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Category;
import me.bobsoft.fsranking.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    private List<Category> categories = new ArrayList<>();

    @Before
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);

        categoryService = new CategoryService(categoryRepository);

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
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        List<Category> categories = categoryService.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(0);
    }

    @Test
    public void findAll2() {
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categories = categoryService.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).isEqualTo(this.categories);
    }

    @Test
    public void findAll3() {
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categories = categoryService.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.get(0).getClass()).isEqualTo(Category.class);
    }
}