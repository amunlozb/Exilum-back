package com.exilum.demo.model.DTO.strategy.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StrategyDTO {
    private List<ItemDTO> scarabs;
    private List<ItemDTO> deliriumOrbs;
    private List<ItemDTO> mapDeviceCraft;
    private List<ItemDTO> maps;
    private List<ItemDTO> craftingMaterials;
}
