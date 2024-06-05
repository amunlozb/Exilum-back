package com.exilum.demo.admin;

import com.exilum.demo.security.Permission;
import com.exilum.demo.security.Role;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
// This service should be callable from a REST endpoint so that administrators are able to grant or deny users permissions.
public class UserManagementService {

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
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }


    public String setRolesNew(String uid, Role requestedRole) throws FirebaseAuthException {
        Map<String, Object> additionalClaims = new HashMap<String, Object>();

        additionalClaims.put(requestedRole.name(), true);

        String customToken = FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);
        return customToken;

    }
    
    public String grantAdminRole(String uid) throws FirebaseAuthException {
        List<Role> roles = Collections.singletonList(Role.ADMIN);
        return setRoleUserClaims(uid, roles);
    }

    public String grantAdminRoleByEmail(String email) throws FirebaseAuthException {
        // Get the user's UID based on their email
        String uid = FirebaseAuth.getInstance().getUserByEmail(email).getUid();

        List<Role> roles = Collections.singletonList(Role.ADMIN);
        return setRoleUserClaims(uid, roles);
    }

    public String revokeAdminRoleByEmail(String email) throws FirebaseAuthException {
        // Get the user's UID based on their email
        String uid = FirebaseAuth.getInstance().getUserByEmail(email).getUid();

        return setRoleUserClaims(uid, Collections.emptyList());
    }
}
