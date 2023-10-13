package com.api.cycleshop;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cycleshop.entity.Cycle;

public interface TestCycleRepository extends JpaRepository<Cycle, Integer>{
    
}
