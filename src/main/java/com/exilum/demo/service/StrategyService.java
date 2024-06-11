package com.exilum.demo.service;

import com.exilum.demo.model.*;
import com.exilum.demo.model.DTO.strategy.request.ItemDTO;
import com.exilum.demo.model.DTO.strategy.request.StrategyDTO;
import com.exilum.demo.model.DTO.strategy.response.ItemSummaryDTO;
import com.exilum.demo.model.DTO.strategy.response.StrategySummaryDTO;
import com.exilum.demo.repository.*;
import com.exilum.demo.service.fetching.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StrategyService {

    @Autowired
    private ScarabFetchingService scarabFetchingService;
    @Autowired
    private DeliriumOrbFetchingService deliriumOrbFetchingService;
    @Autowired
    private CraftingMaterialFetchingService craftingMaterialFetchingService;
    @Autowired
    private MapFetchingService mapFetchingService;
    @Autowired
    private DeviceCraftFetchingService deviceCraftFetchingService;
    @Autowired
    private StrategyRepository strategyRepository;

    @Transactional
    public StrategySummaryDTO processStrategy(StrategyDTO strategyDTO) {
        Strategy strategy = new Strategy();
        strategyRepository.save(strategy);

        List<ItemSummaryDTO> scarabs = new ArrayList<>();
        List<ItemSummaryDTO> deliriumOrbs = new ArrayList<>();
        List<ItemSummaryDTO> mapDeviceCrafts = new ArrayList<>();
        List<ItemSummaryDTO> maps = new ArrayList<>();
        List<ItemSummaryDTO> craftingMaterials = new ArrayList<>();

        // Process Scarabs
        for (ItemDTO item : strategyDTO.getScarabs()) {
            double price = scarabFetchingService.findPriceByName(item.getName());
            String iconUrl = item.getIcon_url();
            scarabs.add(new ItemSummaryDTO(item.getName(), item.getQuantity(), price, iconUrl));
            Scarab scarab = scarabFetchingService.findByName(item.getName());
            strategy.addScarab(scarab, item.getQuantity());
        }

        // Process Delirium Orbs
        for (ItemDTO item : strategyDTO.getDeliriumOrbs()) {
            double price = deliriumOrbFetchingService.findPriceByName(item.getName());
            String iconUrl = item.getIcon_url();
            deliriumOrbs.add(new ItemSummaryDTO(item.getName(), item.getQuantity(), price, iconUrl));
            DeliriumOrb deliriumOrb = deliriumOrbFetchingService.findByName(item.getName());
            strategy.addDeliriumOrb(deliriumOrb, item.getQuantity());
        }

        // Process Map Device Crafts
        for (ItemDTO item : strategyDTO.getMapDeviceCraft()) {
            double price = deviceCraftFetchingService.findPriceByName(item.getName());
            // Static icon url for device crafts
            mapDeviceCrafts.add(new ItemSummaryDTO(item.getName(), item.getQuantity(), price, "https://web.poecdn.com/gen/image/WzI1LDE0LHsiZiI6IjJESXRlbXMvQ3VycmVuY3kvQmVzdGlhcnlPcmJGdWxsIiwidyI6MSwiaCI6MSwic2NhbGUiOjF9XQ/3214b44360/BestiaryOrbFull.png"));
            DeviceCraft deviceCraft = deviceCraftFetchingService.findByName(item.getName());
            strategy.addDeviceCraft(deviceCraft, item.getQuantity());
        }

        // Process Maps
        for (ItemDTO item : strategyDTO.getMaps()) {
            double price = mapFetchingService.findPriceByName(item.getName());
            String iconUrl = item.getIcon_url();
            maps.add(new ItemSummaryDTO(item.getName(), item.getQuantity(), price, iconUrl));
            Map map = mapFetchingService.findByName(item.getName());
            strategy.addMap(map, item.getQuantity());
        }

        // Process Crafting Materials
        for (ItemDTO item : strategyDTO.getCraftingMaterials()) {
            double price = craftingMaterialFetchingService.findPriceByName(item.getName());
            String iconUrl = item.getIcon_url(); // Get the icon URL
            craftingMaterials.add(new ItemSummaryDTO(item.getName(), item.getQuantity(), price, iconUrl));
            CraftingMaterial craftingMaterial = craftingMaterialFetchingService.findByName(item.getName());
            strategy.addCraftingMaterial(craftingMaterial, item.getQuantity());
        }
        strategyRepository.save(strategy);

        return new StrategySummaryDTO(scarabs, deliriumOrbs, mapDeviceCrafts, maps, craftingMaterials);
    }
}
