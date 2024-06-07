package com.exilum.demo.model.DTO.strategy.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StrategySummaryDTO {
    // Getters and setters
    private List<ItemSummaryDTO> scarabs;
    private List<ItemSummaryDTO> deliriumOrbs;
    private List<ItemSummaryDTO> mapDeviceCrafts;
    private List<ItemSummaryDTO> maps;
    private List<ItemSummaryDTO> craftingMaterials;

    public StrategySummaryDTO(List<ItemSummaryDTO> scarabs, List<ItemSummaryDTO> deliriumOrbs, List<ItemSummaryDTO> mapDeviceCrafts, List<ItemSummaryDTO> maps, List<ItemSummaryDTO> craftingMaterials) {
        this.scarabs = scarabs;
        this.deliriumOrbs = deliriumOrbs;
        this.mapDeviceCrafts = mapDeviceCrafts;
        this.maps = maps;
        this.craftingMaterials = craftingMaterials;
    }

}
