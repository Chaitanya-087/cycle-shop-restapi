package com.api.cycleshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cycleshop.entity.Cycle;

public interface CycleRepository extends JpaRepository<Cycle, Integer> {

}