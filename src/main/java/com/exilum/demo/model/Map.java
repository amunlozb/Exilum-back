package com.exilum.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Map {
    @Id
    @Column(name = "map_id")
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer map_id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon_url")
    private String icon_url;

    @Column(name = "map_tier")
    private String map_tier;

    @Column(name = "price")
    private double price;
}