package com.exilum.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CraftingMaterials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CraftingMaterial {
    @Id
    @Column(name = "material_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "icon_url")
    private String icon_url;
}