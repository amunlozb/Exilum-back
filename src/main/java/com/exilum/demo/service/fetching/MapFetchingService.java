package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.Map;
import com.exilum.demo.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}