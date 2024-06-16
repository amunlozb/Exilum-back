package com.exilum.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "DeliriumOrbs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliriumOrb {
    @Id
    @Column(name = "delirium_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "reward")
    private String reward;

    @Column(name = "price")
    private Double price;

    @Column(name = "icon_url")
    private String icon_url;

}
