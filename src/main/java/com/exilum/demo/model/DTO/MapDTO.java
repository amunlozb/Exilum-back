package com.exilum.demo.model.DTO;

import lombok.Getter;

@Getter
public class MapDTO {
    private Integer id;
    private String name;
    private String mapTier;
    private Double mean;
    private String icon;

    @Override
    public String toString() {
        return "MapDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mapTier=" + mapTier +
                ", mean=" + mean +
                ", icon='" + icon + '\'' +
                '}';
    }
}
