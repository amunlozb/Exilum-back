package com.exilum.demo.model.DTO;

import lombok.Getter;

@Getter
public class CraftingMaterialDTO {
    private Long id;
    private String name;
    private Double mean;
    private String icon;

    @Override
    public String toString() {
        return "CraftingMaterialDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mean=" + mean +
                ", icon='" + icon + '\'' +
                '}';
    }
}
