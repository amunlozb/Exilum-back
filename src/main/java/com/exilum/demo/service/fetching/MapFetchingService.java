package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.DeliriumOrb;
import com.exilum.demo.model.Map;
import com.exilum.demo.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MapFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=map&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MapRepository mapRepository;

    public MapDTO[] fetchMaps() {
        MapDTO[] result = restTemplate.getForObject(uri, MapDTO[].class);

        return result;
    }

    public String fetchAndSaveMaps() {
        MapDTO[] mapDTOs = fetchMaps();

        if (mapDTOs != null) {
            for (MapDTO mapDTO : mapDTOs) {
                Map map = new Map();
                // map.setMap_id(Math.toIntExact(mapDTO.getId()));
                map.setName(mapDTO.getName());
                map.setIcon_url(mapDTO.getIcon());
                map.setMap_tier(mapDTO.getMapTier());
                map.setPrice(mapDTO.getMean());

                mapRepository.save(map);
            }
            return "Maps saved successfully";
        } else {
            return "An error occurred while fetching maps. Check poe.watch status.";
        }
    }

    public String updatePricesMaps() {
        MapDTO[] mapDTOs = fetchMaps();
        if (mapDTOs != null) {
            for (MapDTO mapDTO : mapDTOs) {
                List<Map> auxMapList = mapRepository.findByName(mapDTO.getName());
                for (Map map : auxMapList) {
                    // Check if the map's tier matches the tier of the MapDTO
                    // or if both map's tier and MapDTO's tier are null
                    if ((map.getMap_tier() == null && mapDTO.getMapTier() == null) ||
                            (map.getMap_tier() != null && map.getMap_tier().equals(mapDTO.getMapTier()))) {
                        map.setPrice(mapDTO.getMean());
                        mapRepository.save(map);
                        break; // Once a match is found, exit the loop
                    }
                }
            }
            return "Map prices updated successfully";
        } else {
            return "An error occurred while fetching maps. Check poe.watch status.";
        }
    }



}