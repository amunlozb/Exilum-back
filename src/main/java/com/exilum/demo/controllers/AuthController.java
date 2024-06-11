package com.exilum.demo.controllers;

import com.exilum.demo.admin.UserManagementService;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    private UserManagementService userManagementService;

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
                return ResponseEntity.ok().body(Collections.singletonList("USER"));
            }
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String idToken, @RequestHeader("uid") String uid ) {
        try {
            // Invalidates the firebase refresh token (user has to sign in again to get a new valid token)
            firebaseAuth.revokeRefreshTokens(uid);
            return ResponseEntity.ok("User signed out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public String sayHello() {
        return "this works";
    }

    @PostMapping("/grantAdmin/{uid}")
    public ResponseEntity<?> grantAdminRole(@PathVariable String uid) {
        try {
            String customToken = userManagementService.grantAdminRole(uid);
            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
