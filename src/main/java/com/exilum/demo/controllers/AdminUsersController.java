package com.exilum.demo.controllers;

import com.exilum.demo.model.CreateUserRequest;
import com.exilum.demo.model.UpdateUserRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(createUserRequest.getEmail())
                    .setPassword(createUserRequest.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return ResponseEntity.ok("Successfully created new user");
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(e.getHttpResponse().getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            // Create an UpdateRequest with the provided UID
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(updateUserRequest.getUid())
                    .setEmail(updateUserRequest.getEmail())
                    .setPassword(updateUserRequest.getPassword());

            // Execute the update
            UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
            return ResponseEntity.ok("Successfully updated user: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(e.getHttpResponse().getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uid) {
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
            return ResponseEntity.ok("Successfully deleted user with UID: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(e.getHttpResponse().getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllUsers() {
        try {
            ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
            JsonArray usersArray = new JsonArray();

            while (page != null) {
                for (UserRecord user : page.getValues()) {
                    JsonObject userJson = new JsonObject();
                    userJson.addProperty("uid", user.getUid());
                    userJson.addProperty("email", user.getEmail());

                    usersArray.add(userJson);
                }
                page = page.getNextPage();
            }

            return ResponseEntity.ok(new Gson().toJson(usersArray));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(e.getHttpResponse().getStatusCode()).body(e.getMessage());
        }
    }

}
