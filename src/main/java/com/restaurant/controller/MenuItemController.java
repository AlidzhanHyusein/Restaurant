package com.restaurant.controller;

import com.restaurant.dto.CreateMenuItemRequest;
import com.restaurant.entity.Category;
import com.restaurant.entity.MenuItem;
import com.restaurant.service.CategoryService;
import com.restaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody CreateMenuItemRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());


        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(category);

        MenuItem created = menuItemService.createMenuItem(request.getCategoryId(),menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems(){
        return ResponseEntity.ok(menuItemService.findAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id){
        return ResponseEntity.ok(menuItemService.findMenuItemById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(menuItemService.findByCategoryId(categoryId));
    }

    @GetMapping("/category/{categoryId}/sorted")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategorySorted(@PathVariable Long categoryId){
        return ResponseEntity.ok(menuItemService.getMenuItemsSortedByPrice(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<MenuItem> getMenuItemByName(@RequestParam String name){
        MenuItem menuItem = menuItemService.getMenuItemByName(name).orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
        return ResponseEntity.ok(menuItem);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long menuItemId,@RequestParam String name,@RequestParam BigDecimal price,@RequestParam Long categoryId){
        return ResponseEntity.ok(menuItemService.updateMenuItem(menuItemId, name,price,categoryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItemById(@PathVariable Long id){
        menuItemService.deleteMenuItemById(id);
        return ResponseEntity.noContent().build();
    }
}
