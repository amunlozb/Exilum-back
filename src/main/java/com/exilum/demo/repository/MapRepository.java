package com.exilum.demo.repository;

import com.exilum.demo.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {
    List<Map> findByName(String name);
    List<Map> findByMapTier(String tier);

    @Query("SELECT m FROM Map m WHERE m.mapTier BETWEEN :min AND :max")
    List<Map> findByMapTierBetween(int min, int max);
    List<Map> findByMapTierIsNull();
}
