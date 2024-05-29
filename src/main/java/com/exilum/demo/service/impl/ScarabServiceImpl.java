package com.exilum.demo.service.impl;

import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.ScarabRepository;
import com.exilum.demo.service.ScarabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScarabServiceImpl implements ScarabService {
    @Autowired
    private ScarabRepository scarabRepository;

    @Override
    @Transactional
    public Scarab addScarab(Scarab scarab) {
        return scarabRepository.save(scarab);
    }
}
