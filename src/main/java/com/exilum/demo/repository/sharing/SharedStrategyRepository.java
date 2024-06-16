package com.exilum.demo.repository.sharing;

import com.exilum.demo.model.sharing.SharedStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SharedStrategyRepository extends JpaRepository<SharedStrategy, UUID> {
}
