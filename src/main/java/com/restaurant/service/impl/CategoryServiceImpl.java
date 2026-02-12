package com.restaurant.service.impl;

import com.restaurant.entity.Category;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new IllegalArgumentException("Category with this name already exists");
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Category id cannot be null");
        }
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return category;
    }

    @Override
    public Category getCategoryByName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name cannot be null or blank");
        }

        return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public Category updateCategory(Long categoryId, String newName) {
        Category category = getCategoryById(categoryId);
        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public boolean existsByName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name cannot be null or blank");
        }

        return categoryRepository.existsByName(name);
    }
}
