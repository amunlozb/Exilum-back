package com.exilum.demo.repository;

import com.exilum.demo.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {
    List<Map> findByName(String name);
}
