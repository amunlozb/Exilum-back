package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.CraftingMaterialDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CraftingMaterialFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=currency&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public CraftingMaterialDTO[] fetchCraftingMaterials() {
        List<String> materials = Arrays.asList("Cartographer's Chisel", "Orb of Alchemy", "Vaal Orb", "Orb of Scouring");
        // Get result with all currency types
        CraftingMaterialDTO[] result = restTemplate.getForObject(uri, CraftingMaterialDTO[].class);

        // Filter result to only include chisels, alchemy orbs, vaal orbs, scouring orbs
        return Arrays.stream(result)
                .filter(c -> materials.contains(c.getName()))
                .collect(Collectors.toList())
                .toArray(new CraftingMaterialDTO[0]);
    }
}
