package com.restaurant.service;

import com.restaurant.entity.Category;
import jakarta.persistence.Column;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    Category updateCategory(Long categoryId,String newName);

    void deleteCategoryById(Long id);

    boolean existsByName(String name);
}
