package com.exilum.demo.controllers;

import com.exilum.demo.model.DTO.strategy.request.StrategyDTO;
import com.exilum.demo.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/strategy")
public class StrategyController {
    @Autowired
    private StrategyService strategyService;

    @PostMapping("/price")
    public double calculateStrategyPrice(@RequestBody StrategyDTO strategyDTO) {
        return strategyService.processStrategy(strategyDTO);
    }
}
