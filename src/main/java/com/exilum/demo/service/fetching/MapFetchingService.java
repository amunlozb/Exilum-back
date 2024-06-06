package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.DeliriumOrb;
import com.exilum.demo.model.Map;
import com.exilum.demo.model.Tier;
import com.exilum.demo.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=map&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MapRepository mapRepository;

    public MapDTO[] fetchMaps() {

        return restTemplate.getForObject(uri, MapDTO[].class);
    }

    public String fetchAndSaveMaps() {
        MapDTO[] mapDTOs = fetchMaps();

        if (mapDTOs != null) {
            for (MapDTO mapDTO : mapDTOs) {
                Map map = new Map();
                // map.setMap_id(Math.toIntExact(mapDTO.getId()));
                map.setName(mapDTO.getName());
                map.setIcon_url(mapDTO.getIcon());
                map.setMapTier(mapDTO.getMapTier());
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
                    if ((map.getMapTier() == null && mapDTO.getMapTier() == null) ||
                            (map.getMapTier() != null && map.getMapTier().equals(mapDTO.getMapTier()))) {
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

    public List<Map> findByTier(String tier) {
        if (tier.equals(Tier.WHITE.name())) {
            System.out.println("Entering White Condition");
            return mapRepository.findByMapTierBetween(1, 5).stream()

                    // remove blighted maps (they will come from a separate method)
                    .filter(map -> !map.getName().contains("Blight"))
                    .filter(map -> Integer.parseInt(map.getMapTier()) >= 1 && Integer.parseInt(map.getMapTier()) <= 5)
                    .sorted(Comparator.comparing(Map::getMapTier))
                    .collect(Collectors.toList());

        } else if (tier.equals(Tier.YELLOW.name())) {
            return mapRepository.findAll().stream()
                    // remove blighted maps (they will come from a separate method)
                    .filter(map -> !map.getName().contains("Blight"))
                    .filter(map -> !(map.getMapTier() == null))
                    .filter(map -> Integer.parseInt(map.getMapTier()) >= 6 && Integer.parseInt(map.getMapTier()) <= 10)
                    .sorted(Comparator.comparing(Map::getMapTier))
                    .collect(Collectors.toList());

        } else if (tier.equals(Tier.RED.name())) {
            return mapRepository.findByMapTierBetween(11, 16).stream()
                    // remove blighted maps (they will come from a separate method)
                    .filter(map -> !map.getName().contains("Blight"))
                    .filter(map -> Integer.parseInt(map.getMapTier()) >= 11 && Integer.parseInt(map.getMapTier()) <= 16)
                    .sorted(Comparator.comparing(Map::getMapTier))
                    .collect(Collectors.toList());

        } else if (tier.equals( Tier.T17.name())) {
            return mapRepository.findByMapTier("17").stream()
                    // remove blighted maps (they will come from a separate method)
                    .filter(map -> !map.getName().contains("Blight"))
                    .collect(Collectors.toList());

        } else if (tier.equals( Tier.OTHER.name())) {
            return mapRepository.findByMapTierIsNull();
        } else {
            return null;
        }
    }

    // adding just in case its needed in the future, currently not used
    public List<Map> getAllMaps() {
        List<Map> maps = mapRepository.findAll();
        return maps.stream()
                .sorted(Comparator.comparing(Map::getMapTier, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    public List<Map> getBlightedMaps() {
        List<Map> allMaps = getAllMaps();
        return allMaps.stream()
                .filter(map -> map.getName().contains("Blighted"))
                .collect(Collectors.toList());
    }
}
