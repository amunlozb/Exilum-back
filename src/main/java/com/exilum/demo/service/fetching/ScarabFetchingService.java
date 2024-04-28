package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.ScarabDTO;
import com.exilum.demo.model.Scarab;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScarabFetchingService {

    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=scarab&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public ScarabDTO[] fetchScarabs() {
        ScarabDTO[] result = restTemplate.getForObject(uri, ScarabDTO[].class);

        return result;
    }
}
