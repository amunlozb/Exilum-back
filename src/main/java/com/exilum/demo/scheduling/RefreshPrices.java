package com.exilum.demo.scheduling;


import com.exilum.demo.ExilumApplication;
import com.exilum.demo.service.fetching.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class RefreshPrices {
    @Autowired
    ScarabFetchingService scarabFetchingService;
    @Autowired
    MapFetchingService mapFetchingService;
    @Autowired
    DeliriumOrbFetchingService deliriumOrbFetchingService;
    @Autowired
    DeviceCraftFetchingService deviceCraftFetchingService;
    @Autowired
    CraftingMaterialFetchingService craftingMaterialFetchingService;

    private static final Logger log = LoggerFactory.getLogger(RefreshPrices.class);

    // Run the scheduled task every
    @Scheduled(fixedDelay = 1800000)
    public void refreshPrices() {
        logTaskStart("Refreshing prices");
        updatePrices("scarab", scarabFetchingService::updatePricesScarabs);
        updatePrices("map", mapFetchingService::updatePricesMaps);
        updatePrices("delirium orb", deliriumOrbFetchingService::updatePricesDeliriumOrbs);
        updatePrices("crafting material", craftingMaterialFetchingService::updatePricesCraftingMaterials);
        logTaskEnd("Refreshing prices");
    }

    private void logTaskStart(String taskName) {
        log.info("TASK STARTING: {}...", taskName);
    }

    private void logTaskEnd(String taskName) {
        log.info("TASK ENDING: {}...", taskName);
    }

    private void updatePrices(String itemName, Runnable updateMethod) {
        log.info("Updating {} prices...", itemName);
        updateMethod.run();
        log.info("{} prices updated.", itemName);
    }

}
