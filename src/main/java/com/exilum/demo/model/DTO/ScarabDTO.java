package com.exilum.demo.model.DTO;

import lombok.Getter;

@Getter
public class ScarabDTO {
    private Integer id;
    private String name;
    private String icon;
    private Double mean;

    @Override
    public String toString() {
        return "ScarabDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", mean=" + mean +
                '}';
    }
}
