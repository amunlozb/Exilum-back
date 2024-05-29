package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DeliriumOrb;
import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.repository.DeliriumOrbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class DeliriumOrbFetchingService {
    // poe.watch documentation: https://docs.poe.watch/#categories
    String uri = "https://api.poe.watch/get?category=deliriumOrb&league=Necropolis";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private DeliriumOrbRepository deliriumOrbRepository;

    public DeliriumOrbDTO[] fetchDeliriumOrbs() {
        DeliriumOrbDTO[] result = restTemplate.getForObject(uri, DeliriumOrbDTO[].class);

        return result;
    }

    public String fetchAndSaveDeliriumOrbs() {
        DeliriumOrbDTO[] deliriumOrbDTOs = fetchDeliriumOrbs();

        if (deliriumOrbDTOs != null) {

            for (DeliriumOrbDTO deliriumOrbDTO : deliriumOrbDTOs) {
                DeliriumOrb deliriumOrb = new DeliriumOrb();

                deliriumOrb.setId(12);
                deliriumOrb.setName(deliriumOrbDTO.getName());
                deliriumOrb.setPrice(deliriumOrbDTO.getMean());
                deliriumOrb.setIcon_url(deliriumOrbDTO.getIcon());

                deliriumOrbRepository.save(deliriumOrb);
            }
            return "Delirium Orbs saved successfully";
        } else {
            return "An error occurred while fetching Delirium Orbs. Check poe.watch status.";
        }
    }

    public String updatePricesDeliriumOrbs() {
        DeliriumOrbDTO[] deliriumOrbDTOs = fetchDeliriumOrbs();

        if (deliriumOrbDTOs != null) {
            for (DeliriumOrbDTO deliriumOrbDTO : deliriumOrbDTOs) {
                DeliriumOrb deliriumOrb = deliriumOrbRepository.findByName(deliriumOrbDTO.getName());
                if (deliriumOrb != null) {
                    deliriumOrb.setPrice(deliriumOrbDTO.getMean());
                    deliriumOrbRepository.save(deliriumOrb);
                }
            }
            return "Delirium Orb prices updated successfully";
        } else {
            return "An error occurred while fetching Delirium Orbs. Check poe.watch status.";
        }
    }
}