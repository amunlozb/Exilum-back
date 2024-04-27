package com.exilum.demo.admin;

import com.exilum.demo.security.Permission;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserManagementService userManagementService;

    // Only allow users with ROLE_ANONYMOUS (all users have it by default)
    @Secured("ROLE_ANONYMOUS")
    @PostMapping(path = "/user-claims/{uid}")
    public void setUserClaims(
            @PathVariable String uid,
            @RequestBody List<Permission> requestedClaims
    ) throws FirebaseAuthException {
        userManagementService.setUserClaims(uid, requestedClaims);
    }
}
