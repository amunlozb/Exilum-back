package com.exilum.demo.admin;

import com.exilum.demo.security.Permission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// This service should be callable from a REST endpoint so that administrators are able to grant or deny users permissions.
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setUserClaims(String uid, List<Permission> requestedPermissions) throws FirebaseAuthException {

        // Take user id and a list of Permissions, then convert the permissions to a list of Strings
        List<String> permissions = requestedPermissions
                .stream()
                .map(Enum::toString)
                .toList();

        /*
        Map for sending to firebase auth:
            Key: property inside the jwt token where the customer claims will be
            Value: list of permissions
        */
        Map<String, Object> claims = Map.of("custom_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }
}
