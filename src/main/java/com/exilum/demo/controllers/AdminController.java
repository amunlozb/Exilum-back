package com.exilum.demo.controllers;

import com.exilum.demo.admin.UserManagementService;
import com.exilum.demo.security.Permission;
import com.exilum.demo.service.fetching.ScarabFetchingService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    ScarabFetchingService scarabFetchingService;

    private final UserManagementService userManagementService;

    @PostMapping(path = "/user-claims/{uid}")
    public void setUserClaims(
            @PathVariable String uid,
            @RequestBody List<Permission> requestedClaims
    ) throws FirebaseAuthException {
        userManagementService.setUserClaims(uid, requestedClaims);
    }

    @GetMapping("/testScarabs")
    public String testScarabs() {
        return scarabFetchingService.fetchScarabs();
    }
}
