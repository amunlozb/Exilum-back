package com.exilum.demo.repository;

import com.exilum.demo.model.DeviceCraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCraftRepository extends JpaRepository<DeviceCraft, Integer> {
}
