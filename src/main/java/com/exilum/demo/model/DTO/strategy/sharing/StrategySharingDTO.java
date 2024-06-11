package com.exilum.demo.model.DTO.strategy.sharing;

import com.exilum.demo.model.DTO.strategy.request.ItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StrategySharingDTO {
    private List<ItemDTO> scarabs;
    private List<ItemDTO> deliriumOrbs;
    private List<ItemDTO> mapDeviceCrafts;
    private List<ItemDTO> maps;
    private List<ItemDTO> craftingMaterials;
}
