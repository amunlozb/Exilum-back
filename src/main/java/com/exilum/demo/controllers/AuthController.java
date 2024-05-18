package com.exilum.demo.controllers;

import com.google.firebase.auth.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exilum.demo.model.auth.SignupRequest;
import java.net.URI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private FirebaseAuth firebaseAuth;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest request) {
        try {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(request.getEmail())
                    .setPassword(request.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(createRequest);
            System.out.println("User created successfully: " + userRecord.getUid());

            return ResponseEntity.ok("User created successfully");
        } catch (FirebaseAuthException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestHeader("Authorization") String idToken) {
        try {
            // Verify the ID token while checking if the token is revoked
            boolean checkRevoked = true;

            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken, checkRevoked);
            Map<String, Object> claims = decodedToken.getClaims();

            // check roles
            if (claims.get("custom_claims") != null) {
                List<String> userRoles = (List<String>) claims.get("custom_claims");
                return ResponseEntity.ok().body(userRoles);
            } else {
                return ResponseEntity.ok().body("User has no roles");
            }
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/test")
    public String sayHello() {
        return "this works";
    }
}
