package com.exilum.demo.controllers;

import com.exilum.demo.model.Map;
import com.exilum.demo.model.Tier;
import com.exilum.demo.service.fetching.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {
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

    @GetMapping(path = "/getName")
    public String getUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping(path = "/getRoles")
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getRole(@CurrentSecurityContext SecurityContext context ) {
        return context.getAuthentication().getAuthorities();
    }

    @GetMapping(path = "checkRead")
    @PreAuthorize("hasAuthority('READ')")
    public String hasRead(Principal principal) {
        return principal.getName() + ", you have READ authority";
    }

    @GetMapping(path = "/getScarabs")
    public ResponseEntity<Object> getScarabs() {
        return ResponseEntity.ok(scarabFetchingService.getAllScarabs());
    }

    @GetMapping(path = "/getMaps")
    public ResponseEntity<Object> getMaps() {
        return ResponseEntity.ok(mapFetchingService.getAllMaps());
    }

    @GetMapping(path = "/getDeliriumOrbs")
    public ResponseEntity<Object> getDeliriumOrbs() {
        return ResponseEntity.ok(deliriumOrbFetchingService.getAllDeliriumOrbs());
    }

    @GetMapping(path = "/getCraftingMaterials")
    public ResponseEntity<Object> getCraftingMaterials() {
        return ResponseEntity.ok(craftingMaterialFetchingService.getAllCraftingMaterials());
    }

    @GetMapping(path = "/getDeviceCrafts")
    public ResponseEntity<Object> getDeviceCrafts() {
        return ResponseEntity.ok(deviceCraftFetchingService.getAllDeviceCrafts());
    }

    @GetMapping(path = "getMapsByTier")
    public ResponseEntity<Object> getMapsByTierTest(@RequestParam Tier inputTier) {
        String selectedTier = inputTier.name();
        List<Map> maps = mapFetchingService.findByTier(selectedTier);
        return ResponseEntity.ok(maps);
    }

    @GetMapping(path = "getBlightedMaps")
    public ResponseEntity<Object> getBlightedMaps() {
        return ResponseEntity.ok(mapFetchingService.getBlightedMaps());
    }
}
