package com.exilum.demo.model.auth;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private static final Logger LOGGER = Logger.getLogger(FirebaseAuthenticationToken.class.getName());

    private final FirebaseToken firebaseToken;

    public FirebaseAuthenticationToken(FirebaseToken firebaseToken) {
        super(collectAuthorities(firebaseToken));

        this.firebaseToken = firebaseToken;

        // Log the claims
        LOGGER.info("Claims from FirebaseToken: " + firebaseToken.getClaims());

        // Set authentication status
        setAuthenticated(true);

        // Log authentication status
        LOGGER.info("Authentication status: " + isAuthenticated());
    }

    private static List<GrantedAuthority> collectAuthorities(FirebaseToken firebaseToken) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        firebaseToken.getClaims().entrySet().forEach(entry -> {
            String claimName = entry.getKey();
            if ("custom_claims".equals(claimName)) {
                // Check if custom_claims contain ADMIN
                List<String> customClaims = (List<String>) entry.getValue();
                if (customClaims.contains("ADMIN")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
            } else {
                // For other claims, create authorities based on claim name (eg: USER claim will grant ROLE_USER authority)
                authorities.add(new SimpleGrantedAuthority("ROLE_" + claimName.toUpperCase()));
            }
        });

        return authorities;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}