package com.api.cycleshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cycleshop.entity.Cycle;
import com.api.cycleshop.entity.Order;
import com.api.cycleshop.entity.User;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);
    List<Order> findAll();

    Optional<Order> findByUserAndCycle(User user, Cycle cycle);
}
