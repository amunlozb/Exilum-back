package com.exilum.demo.admin;

import com.exilum.demo.security.Permission;
import com.exilum.demo.security.Role;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
// This service should be callable from a REST endpoint so that administrators are able to grant or deny users permissions.
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public String setRoleUserClaims(String uid, List<Role> requestedRoles) throws FirebaseAuthException {
        // Take user id and a list of Roles, then convert the roles to a list of Strings
        List<String> roles = requestedRoles
                .stream()
                .map(Enum::toString)
                .toList();

        /*
        Map for sending to firebase auth:
            Key: property inside the jwt token where the customer claims will be
            Value: list of permissions
        */
        Map<String, Object> claims = Map.of("custom_claims", roles);


        // return an updated token to the front end, to update the localstorage token value
        try {
            FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
            System.out.println(generateCustomToken(uid));
            return generateCustomToken(uid); // Generate and return a new custom token
        } catch (FirebaseAuthException e) {
            return e.getMessage();
        }
    }

    private String generateCustomToken(String uid) throws FirebaseAuthException {
        return firebaseAuth.createCustomToken(uid);
    }
}
