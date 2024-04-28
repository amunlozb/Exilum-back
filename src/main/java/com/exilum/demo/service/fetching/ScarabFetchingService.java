package com.exilum.demo.service.fetching;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;

@Service
public class ScarabFetchingService {

    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=scarab&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    public String fetchScarabs() {
        // save the results to a String
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }




}
