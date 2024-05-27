package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DTO.ScarabDTO;
import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.ScarabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScarabFetchingService {

    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=scarab&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ScarabRepository scarabRepository;

    public ScarabDTO[] fetchScarabs() {
        ScarabDTO[] result = restTemplate.getForObject(uri, ScarabDTO[].class);

        return result;
    }

    public String fetchAndSaveScarabs() {
        ScarabDTO[] scarabDTOs = restTemplate.getForObject("http://localhost:8080/admin/testScarabs", ScarabDTO[].class);

        if (scarabDTOs != null) {
            for (ScarabDTO scarabDTO : scarabDTOs) {
                Scarab scarab = new Scarab();
                scarab.setScarab_id(scarabDTO.getId());
                scarab.setName(scarabDTO.getName());
                scarab.setIcon_url(scarabDTO.getIcon());
                scarab.setPrice(scarabDTO.getMean());
                // Set other properties as needed

                scarabRepository.save(scarab);
            }
            return "Scarabs saved successfully";
        } else {
            return "An error occurred while fetching scarabs. Check poe.watch status.";
        }
    }


}
