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
            if (menuItem.getName() == null || menuItem.getName().isBlank()) {
                throw new IllegalArgumentException("Menu item name cannot be null or blank");
            }


            Category category = categoryService.getCategoryById(categoryId);
            category.getMenuItems().add(menuItem);
            menuItem.setCategory(category);
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
        public MenuItem findMenuItemById(Long menuItemId) {
            if(menuItemId == null){
                throw new IllegalArgumentException("Category id cannot be null");
            }

            return menuItemRepository.findById(menuItemId).orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
        }

        @Override
        public MenuItem updateMenuItem(Long menuItemId, String name, BigDecimal price, Long categoryId) {
            if(menuItemId == null || name == null || price == null){
                throw new IllegalArgumentException("All parameters are required");
            }


            if(price.compareTo(BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("Price cannot be negative");
            }

            MenuItem menuItem = findMenuItemById(menuItemId);

            menuItem.setName(name);
            menuItem.setPrice(price);

            if (categoryId != null) {
                Category newCategory = categoryService.getCategoryById(categoryId);
                menuItem.setCategory(newCategory);
            }

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
