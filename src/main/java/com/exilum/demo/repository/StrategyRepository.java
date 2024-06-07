package com.exilum.demo.repository;

import com.exilum.demo.model.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Integer> {
}
