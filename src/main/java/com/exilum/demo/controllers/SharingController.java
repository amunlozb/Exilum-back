package com.exilum.demo.controllers;

import com.exilum.demo.model.DTO.strategy.request.StrategyDTO;
import com.exilum.demo.model.DTO.strategy.response.StrategySummaryDTO;
import com.exilum.demo.model.sharing.SharedStrategy;
import com.exilum.demo.repository.sharing.SharedStrategyRepository;
import com.exilum.demo.service.StrategyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/share")
public class SharingController {
    @Autowired
    private SharedStrategyRepository sharedStrategyRepository;
    @Autowired
    private StrategyService strategyService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public UUID createShareLink(@RequestBody StrategyDTO strategyDTO) {
        SharedStrategy sharedStrategy = new SharedStrategy();
        sharedStrategy.setId(UUID.randomUUID());
        // check if toString is alright
        try {
            sharedStrategy.setRequestBody(objectMapper.writeValueAsString(strategyDTO)); // convert request body to string
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process request body", e);
        }

        sharedStrategyRepository.save(sharedStrategy);
        return sharedStrategy.getId();
    }

    @GetMapping("/{uuid}")
    public StrategySummaryDTO getSharedStrategy(@PathVariable UUID uuid) {
        SharedStrategy sharedStrategy = sharedStrategyRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shared link not found"));

        StrategyDTO strategyDTO;
        try {
            strategyDTO = objectMapper.readValue(sharedStrategy.getRequestBody(), StrategyDTO.class); // Convert requestBody (JSON string) back to StrategyDTO
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process request body", e);
        }

        // Use the StrategyService to calculate prices based on the retrieved StrategyDTO
        return strategyService.processStrategy(strategyDTO);
    }
}
