package com.restaurant.service;

import com.restaurant.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    MenuItem createMenuItem(Long categoryId,MenuItem menuItem);

    List<MenuItem> findAllMenuItems();

    List<MenuItem> findByCategoryId(Long categoryId);

    Optional<MenuItem> getMenuItemByName(String name);

    MenuItem findMenuItemById(Long categoryId);

    MenuItem updateMenuItem(Long menuItemId, String name, BigDecimal price, Long categoryId);

    void deleteMenuItemById(Long id);

    List<MenuItem> getMenuItemsSortedByPrice(Long categoryId);

    List<MenuItem> searchMenuItems(String searchTerm);
}
