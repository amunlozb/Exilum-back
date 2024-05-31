package com.exilum.demo.controllers;

import com.exilum.demo.model.Map;
import com.exilum.demo.model.Tier;
import com.exilum.demo.service.fetching.MapFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    MapFetchingService mapFetchingService;

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        String message = "This is a public endpoint.";
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticatedEndpoint() {
        String message = "This is an authenticated endpoint.";
        return ResponseEntity.ok().body(message);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        String message = "This is an admin endpoint.";
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("getMapsByTierTest")
    public ResponseEntity<Object> getMapsByTierTest(@RequestParam Tier inputTier) {
        String selectedTier = inputTier.name();
        List<Map> maps = mapFetchingService.findByTier(selectedTier);
        return ResponseEntity.ok(maps);
    }

    @GetMapping("getBlightedMaps")
    public ResponseEntity<Object> getBlightedMaps() {
        return ResponseEntity.ok(mapFetchingService.getBlightedMaps());
    }
}
