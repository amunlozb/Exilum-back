package com.exilum.demo.service;

import com.exilum.demo.model.*;
import com.exilum.demo.model.DTO.strategy.request.ItemDTO;
import com.exilum.demo.model.DTO.strategy.request.StrategyDTO;
import com.exilum.demo.repository.ScarabRepository;
import com.exilum.demo.repository.DeliriumOrbRepository;
import com.exilum.demo.repository.CraftingMaterialRepository;
import com.exilum.demo.repository.MapRepository;
import com.exilum.demo.repository.DeviceCraftRepository;
import com.exilum.demo.repository.StrategyRepository;
import com.exilum.demo.service.fetching.CraftingMaterialFetchingService;
import com.exilum.demo.service.fetching.DeliriumOrbFetchingService;
import com.exilum.demo.service.fetching.ScarabFetchingService;
import com.exilum.demo.service.fetching.MapFetchingService;
import com.exilum.demo.service.fetching.DeviceCraftFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public double processStrategy(StrategyDTO strategyDTO) {
        double totalPrice = 0;

        Strategy strategy = new Strategy();
        strategyRepository.save(strategy);

        // Process Scarabs
        for (ItemDTO item : strategyDTO.getScarabs()) {
            double price = scarabFetchingService.findPriceByName(item.getName());
            totalPrice += price * item.getQuantity();
            Scarab scarab = scarabFetchingService.findByName(item.getName());
            strategy.addScarab(scarab, item.getQuantity());
        }

        // Process Delirium Orbs
        for (ItemDTO item : strategyDTO.getDeliriumOrbs()) {
            double price = deliriumOrbFetchingService.findPriceByName(item.getName());
            totalPrice += price * item.getQuantity();
            DeliriumOrb deliriumOrb = deliriumOrbFetchingService.findByName(item.getName());
            strategy.addDeliriumOrb(deliriumOrb, item.getQuantity());
        }

        // Process Map Device Crafts
        for (ItemDTO item : strategyDTO.getMapDeviceCraft()) {
            double price = deviceCraftFetchingService.findPriceByName(item.getName());
            totalPrice += price * item.getQuantity();
            DeviceCraft deviceCraft = deviceCraftFetchingService.findByName(item.getName());
            strategy.addDeviceCraft(deviceCraft, item.getQuantity());
        }

        // Process Maps
        for (ItemDTO item : strategyDTO.getMaps()) {
            double price = mapFetchingService.findPriceByName(item.getName());
            totalPrice += price * item.getQuantity();
            Map map = mapFetchingService.findByName(item.getName());
            strategy.addMap(map, item.getQuantity());
        }

        // Process Crafting Materials
        for (ItemDTO item : strategyDTO.getCraftingMaterials()) {
            double price = craftingMaterialFetchingService.findPriceByName(item.getName());
            totalPrice += price * item.getQuantity();
            CraftingMaterial craftingMaterial = craftingMaterialFetchingService.findByName(item.getName());
            strategy.addCraftingMaterial(craftingMaterial, item.getQuantity());
        }

        strategyRepository.save(strategy);
        return totalPrice;
    }
}
