package com.exilum.demo.service.fetching;

import com.exilum.demo.model.CraftingMaterial;
import com.exilum.demo.model.DTO.CraftingMaterialDTO;
import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.CraftingMaterialRepository;
import com.exilum.demo.repository.DeliriumOrbRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CraftingMaterialFetchingService {
    private final DeliriumOrbRepository deliriumOrbRepository;
    private final CraftingMaterialRepository craftingMaterialRepository;
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=currency&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public CraftingMaterialFetchingService(DeliriumOrbRepository deliriumOrbRepository, CraftingMaterialRepository craftingMaterialRepository) {
        this.deliriumOrbRepository = deliriumOrbRepository;
        this.craftingMaterialRepository = craftingMaterialRepository;
    }

    public CraftingMaterialDTO[] fetchCraftingMaterials() {
        List<String> materials = Arrays.asList("Cartographer's Chisel", "Orb of Alchemy", "Vaal Orb", "Orb of Scouring");
        // Get result with all currency types
        CraftingMaterialDTO[] result = restTemplate.getForObject(uri, CraftingMaterialDTO[].class);

        // Filter result to only include chisels, alchemy orbs, vaal orbs, scouring orbs
        return Arrays.stream(result)
                .filter(c -> materials.contains(c.getName()))
                .toList()
                .toArray(new CraftingMaterialDTO[0]);
    }

    public String fetchAndSaveCraftingMaterials() {
        CraftingMaterialDTO[] craftingMaterialDTOs = fetchCraftingMaterials();

        if (craftingMaterialDTOs != null) {
            for (CraftingMaterialDTO craftingMaterialDTO : craftingMaterialDTOs) {
                CraftingMaterial craftingMaterial = new CraftingMaterial();

                craftingMaterial.setId(craftingMaterialDTO.getId());
                craftingMaterial.setName(craftingMaterialDTO.getName());
                craftingMaterial.setPrice(craftingMaterialDTO.getMean());
                craftingMaterial.setIcon_url(craftingMaterialDTO.getIcon());

                craftingMaterialRepository.save(craftingMaterial);
            }
            return "Crafting Materials saved succesfully";
        } else {
            return "An error occurred while fetching Crafting Materials. Check poe.watch status";
        }
    }

    public String updatePricesCraftingMaterials() {
        CraftingMaterialDTO[] craftingMaterialDTOs = fetchCraftingMaterials();

        if (craftingMaterialDTOs != null) {
            for (CraftingMaterialDTO craftingMaterialDTO : craftingMaterialDTOs) {
                CraftingMaterial craftingMaterial = craftingMaterialRepository.findByName(craftingMaterialDTO.getName());
                if (craftingMaterial != null) {
                    craftingMaterial.setPrice(craftingMaterialDTO.getMean());
                    craftingMaterialRepository.save(craftingMaterial);
                }
            }
            return "Delirium Orb prices updated successfully";
        } else {
            return "An error occurred while fetching Delirium Orbs. Check poe.watch status.";
        }
    }

    public List<CraftingMaterial> getAllCraftingMaterials() {
        return craftingMaterialRepository.findAll();
    }

    public Double findPriceByName(String name) {
        CraftingMaterial found = craftingMaterialRepository.findByName(name);
        if (found != null) {
            return found.getPrice();
        }
        return 0d;
    }

    public CraftingMaterial findByName(String name) {
        return(craftingMaterialRepository.findByName(name));
    }
}


