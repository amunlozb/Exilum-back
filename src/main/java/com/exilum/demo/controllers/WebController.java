package com.exilum.demo.controllers;

import com.exilum.demo.model.DeliriumOrb;
import com.exilum.demo.model.Map;
import com.exilum.demo.model.Scarab;
import com.exilum.demo.service.fetching.DeliriumOrbFetchingService;
import com.exilum.demo.service.fetching.MapFetchingService;
import com.exilum.demo.service.fetching.ScarabFetchingService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    ScarabFetchingService scarabFetchingService;

    @Autowired
    MapFetchingService mapFetchingService;
    @Autowired
    DeliriumOrbFetchingService deliriumOrbFetchingService;

    @GetMapping(path = "/getName")
    public String getUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping(path = "/getRoles")
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getRole(@CurrentSecurityContext SecurityContext context ) {
        return context.getAuthentication().getAuthorities();
    }

    @GetMapping(path = "checkRead")
    @PreAuthorize("hasAuthority('READ')")
    public String hasRead(Principal principal) {
        return principal.getName() + ", you have READ authority";
    }

    @GetMapping(path = "/getScarabs")
    public ResponseEntity<Object> getScarabs() {
        return ResponseEntity.ok(scarabFetchingService.getAllScarabs());
    }

    @GetMapping(path = "/getMaps")
    public ResponseEntity<Object> getMaps() {
        List<Map> maps = mapFetchingService.getAllMaps();
        return ResponseEntity.ok(maps);
    }

    @GetMapping(path = "/getDeliriumOrbs")
    public ResponseEntity<Object> getDeliriumOrbs() {
        List<DeliriumOrb> deliriumOrbs = deliriumOrbFetchingService.getAllDeliriumOrbs();
        return ResponseEntity.ok(deliriumOrbs);
    }
}
