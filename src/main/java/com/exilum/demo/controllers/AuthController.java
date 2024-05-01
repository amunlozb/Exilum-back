package com.exilum.demo.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exilum.demo.model.auth.SignupRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private FirebaseAuth firebaseAuth;

    @PostMapping("/signup")
    public UserRecord createUser(@RequestBody SignupRequest request) throws FirebaseAuthException {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword());
        System.out.println("EMAIL: " + request.getEmail() + ",  PASSWORD: " + request.getPassword());

        return FirebaseAuth.getInstance().createUser(createRequest);
    }

    @GetMapping("test")
    public String sayHello() {
        return "this works";
    }
}
