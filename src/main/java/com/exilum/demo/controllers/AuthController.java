package com.exilum.demo.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exilum.demo.model.auth.SignupRequest;

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

    @GetMapping("test")
    public String sayHello() {
        return "this works";
    }
}
