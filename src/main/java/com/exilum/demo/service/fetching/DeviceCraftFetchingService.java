package com.exilum.demo.service.fetching;

import com.exilum.demo.model.DeliriumOrb;
import com.exilum.demo.model.DeviceCraft;
import com.exilum.demo.repository.DeviceCraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCraftFetchingService {
    @Autowired
    DeviceCraftRepository deviceCraftRepository;

    public List<DeviceCraft> getAllDeviceCrafts() {
        return deviceCraftRepository.findAll();
    }
}
