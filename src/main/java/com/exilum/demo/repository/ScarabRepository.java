package com.exilum.demo.repository;

import com.exilum.demo.model.Scarab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScarabRepository extends JpaRepository<Scarab, Long> {
}
