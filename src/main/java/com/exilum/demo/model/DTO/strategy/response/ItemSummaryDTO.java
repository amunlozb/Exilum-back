package com.exilum.demo.model.DTO.strategy.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSummaryDTO {
    private String name;
    private int quantity;
    private double price;

    public ItemSummaryDTO(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
