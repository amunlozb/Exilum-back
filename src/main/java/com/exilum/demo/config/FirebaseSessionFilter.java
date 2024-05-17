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

public class FirebaseSessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
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
                // Get the auth role from the verified token
                FirebaseAuthenticationToken authentication = new FirebaseAuthenticationToken(decodedToken);
                // Assign it to Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (FirebaseAuthException e) {
                // Otherwise, redirect to "/login"
                response.sendRedirect("/login");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}