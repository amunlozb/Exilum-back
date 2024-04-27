package com.exilum.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class WebController {
    @GetMapping(path = "/getName")
    // Principal = authenticated user
    public String getUserName(Principal principal) {
        return principal.getName();
    }
}
