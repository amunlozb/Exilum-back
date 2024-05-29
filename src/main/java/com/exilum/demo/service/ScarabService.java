package com.exilum.demo.service;

import com.exilum.demo.model.Scarab;
import org.springframework.stereotype.Service;

@Service
public interface ScarabService {
    Scarab addScarab(Scarab scarab);
}
