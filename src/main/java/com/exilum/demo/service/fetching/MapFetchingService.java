package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.DTO.ScarabDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=map&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public MapDTO[] fetchMaps() {
        MapDTO[] result = restTemplate.getForObject(uri, MapDTO[].class);

        return result;
    }
}
