package com.exilum.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DeviceCrafts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCraft {
    @Id
    @Column(name = "craft_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer craft_id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;
}