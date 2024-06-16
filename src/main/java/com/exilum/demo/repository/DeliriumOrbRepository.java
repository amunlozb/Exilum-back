package com.exilum.demo.repository;

import com.exilum.demo.model.DeliriumOrb;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliriumOrbRepository extends JpaRepository<DeliriumOrb, Integer> {
    DeliriumOrb findByName(String name);
}