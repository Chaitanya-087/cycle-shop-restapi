package com.api.cycleshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cycleshop.entity.Cart;
import com.api.cycleshop.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

}