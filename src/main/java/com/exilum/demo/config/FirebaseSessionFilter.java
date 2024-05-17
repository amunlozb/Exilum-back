package com.exilum.demo.config;

import com.exilum.demo.model.auth.FirebaseAuthenticationToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class FirebaseSessionFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(FirebaseSessionFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.info("FirebaseSessionFilter invoked");

        // If session cookie exists, get its value
        String sessionCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("session".equals(cookie.getName())) {
                    sessionCookie = cookie.getValue();
                    break;
                }
            }
        }

        // If session cookie exists
        if (sessionCookie != null) {
            try {
                final boolean checkRevoked = true;
                // Verify it
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifySessionCookie(sessionCookie, checkRevoked);
                // Get the auth from the decoded token
                FirebaseAuthenticationToken authentication = new FirebaseAuthenticationToken(decodedToken);
                // And set it to spring security
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("Authentication set for user: " + decodedToken.getUid());
            } catch (FirebaseAuthException e) {
                LOGGER.severe("Error verifying session cookie: " + e.getMessage());
                response.sendRedirect("/login");
                return;
            }
        } else {
            // Check if the request is for a public endpoint, if so, allow it to proceed without authentication
            String requestUri = request.getRequestURI();
            System.out.println(requestUri);
            LOGGER.info("Entering else (NO SESSION COOKIE FOUND)");
            LOGGER.info("COOKIES FOUND: " + Arrays.toString(request.getCookies()));
            if (requestUri.contains("/api/test/public")) {
                LOGGER.info(requestUri);
                LOGGER.info("Public endpoint accessed without session cookie. Allowing request to proceed.");
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
