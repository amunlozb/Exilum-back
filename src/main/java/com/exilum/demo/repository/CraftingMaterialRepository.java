package com.exilum.demo.repository;

import com.exilum.demo.model.DeliriumOrb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exilum.demo.model.CraftingMaterial;

@Repository
public interface CraftingMaterialRepository extends JpaRepository<CraftingMaterial, Integer> {
    CraftingMaterial findByName(String name);
}
