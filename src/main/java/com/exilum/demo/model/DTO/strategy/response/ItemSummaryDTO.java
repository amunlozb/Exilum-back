package com.exilum.demo.model.DTO.strategy.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSummaryDTO {
    private String name;
    private int quantity;
    private double price;
    private String icon_url;

    public ItemSummaryDTO(String name, int quantity, double price, String icon_url) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.icon_url = icon_url;
    }
}
