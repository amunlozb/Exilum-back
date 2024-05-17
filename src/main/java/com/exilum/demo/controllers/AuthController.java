package com.exilum.demo.controllers;

import com.google.firebase.auth.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exilum.demo.model.auth.SignupRequest;

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

    @PostMapping("/sessionLogin")
    public ResponseEntity<?> createSessionCookie(@RequestBody String requestBody, HttpServletResponse response) {
        // Get the ID token sent by the client
        JsonObject jsonObject = new Gson().fromJson(requestBody, JsonObject.class);
        String idToken = jsonObject.get("idToken").getAsString();

        // Set the expiration time to 5 days
        long expirationTime = TimeUnit.DAYS.toMillis(5);

        // Build the cookie options
        SessionCookieOptions options = SessionCookieOptions.builder()
                .setExpiresIn(expirationTime)
                .build();

        try {
            // Create the session cookie and verify the ID token (the cookie contains the same claims as the token)
            String sessionCookie = FirebaseAuth.getInstance().createSessionCookie(idToken, options);
            Cookie cookie = new Cookie("session", sessionCookie);
            cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(expirationTime));
            cookie.setHttpOnly(true); // Ensures cookie is not accessible via client-side javascript
            cookie.setDomain("localhost"); // TODO change when deployed
            cookie.setPath("/");


            // Add the cookie to the response
            response.addCookie(cookie);
            System.out.println("Session cookie created succesfully");
            return ResponseEntity.ok().build();
        } catch (FirebaseAuthException e) {
            System.err.println("Error creating session cookie" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to create a session cookie: " + e.getMessage());
        }
    }

/*    public ResponseEntity<?> verifySessionCookie(@CookieValue("session") String sessionCookie) {
        try {
            // Verify the session cookie. In this case, an additional check is added to detect
            // if the user's Firebase session was revoked, user deleted/disabled, etc.
            final boolean checkRevoked = true;
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifySessionCookie(sessionCookie, checkRevoked);
            return ResponseEntity.ok().body(serveContentForUser(decodedToken));
        } catch (FirebaseAuthException e) {
            // Session cookie is unavailable, invalid, or revoked. Force user to login.
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/login");
            return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
        }
    }*/



    @GetMapping("/test")
    public String sayHello() {
        return "this works";
    }
}
