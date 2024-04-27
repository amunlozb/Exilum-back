package com.exilum.demo.controllers;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
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

    @GetMapping(path = "/getAuthorities")
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getRole(@CurrentSecurityContext SecurityContext context ) {
        return context.getAuthentication().getAuthorities();
    }
}
