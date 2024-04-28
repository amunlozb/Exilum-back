package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliriumOrbFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=deliriumOrb&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public DeliriumOrbDTO[] fetchDeliriumOrbs() {
        DeliriumOrbDTO[] result = restTemplate.getForObject(uri, DeliriumOrbDTO[].class);

        return result;
    }
}
