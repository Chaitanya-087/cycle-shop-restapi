package com.api.cycleshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cycleshop.entity.CartItem;
import com.api.cycleshop.entity.Cycle;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
    Optional<CartItem> findByCycle(Cycle cycle);
}
