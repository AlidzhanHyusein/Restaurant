package com.restaurant.repository;

import com.restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByCategoryId(Long id);

    Optional<MenuItem> findMenuItemByName(String name);

    List<MenuItem> findByCategoryIdOrderByPriceAsc(Long categoryId);

    List<MenuItem> findByNameContainingIgnoreCase(String name);

}
