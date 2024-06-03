package com.exilum.demo.model.DTO;

import lombok.Getter;

@Getter
public class DeviceCraftDTO {
    private Integer id;
    private String name;
    private Double mean;
    private String icon;

    @Override
    public String toString() {
        return "DeviceCraftDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mean=" + mean +
                ", icon='" + icon + '\'' +
                '}';
    }
}
