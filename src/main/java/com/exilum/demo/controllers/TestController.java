package com.exilum.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        String message = "This is a public endpoint.";
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticatedEndpoint() {
        String message = "This is an authenticated endpoint.";
        return ResponseEntity.ok().body(message);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        String message = "This is an admin endpoint.";
        return ResponseEntity.ok().body(message);
    }
}
