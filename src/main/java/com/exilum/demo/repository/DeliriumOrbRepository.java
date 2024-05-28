package com.exilum.demo.repository;

import com.exilum.demo.model.DeliriumOrb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliriumOrbRepository extends JpaRepository<DeliriumOrb, Integer> {
}