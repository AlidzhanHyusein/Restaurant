package com.restaurant.service.impl;

import com.restaurant.entity.Category;
import com.restaurant.entity.MenuItem;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.service.CategoryService;
import com.restaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryService categoryService;

    @Override
    public MenuItem createMenuItem(Long categoryId, MenuItem menuItem) {
        if(menuItem == null || menuItem.getPrice().compareTo(BigDecimal.ZERO) <= 0 || categoryId == null){
            throw new IllegalArgumentException("Menu item cannot be null or price cannot be negative");
        }

        Category category = categoryService.getCategoryById(categoryId);
        category.getMenuItems().add(menuItem);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> findAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> findByCategoryId(Long categoryId) {
        if(categoryId == null){
            throw new IllegalArgumentException("Category id cannot be null");
        }

        return menuItemRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Optional<MenuItem> getMenuItemByName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Menu item name cannot be null or blank");
        }
        return menuItemRepository.findMenuItemByName(name);
    }

    @Override
    public MenuItem findMenuItemById(Long categoryId) {
        if(categoryId == null){
            throw new IllegalArgumentException("Category id cannot be null");
        }

        return menuItemRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
    }

    @Override
    public MenuItem updateMenuItem(Long menuItemId, String name, BigDecimal price, Long categoryId) {
        if(menuItemId == null || name == null || price == null || categoryId == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }

        Category category = categoryService.getCategoryById(categoryId);
        MenuItem menuItem = category.getMenuItems().stream().filter(items -> items.getId().equals(menuItemId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

      menuItem.setName(name);
      menuItem.setPrice(price);
      return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItemById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Menu item id cannot be null");
        }

        menuItemRepository.deleteById(id);
    }

    @Override
    public List<MenuItem> getMenuItemsSortedByPrice(Long categoryId) {
        if(categoryId == null){
            throw new IllegalArgumentException("Category id cannot be null");
        }

        return menuItemRepository.findByCategoryIdOrderByPriceAsc(categoryId);
    }

    @Override
    public List<MenuItem> searchMenuItems(String searchTerm) {
        return menuItemRepository.findByNameContainingIgnoreCase(searchTerm);
    }
}
