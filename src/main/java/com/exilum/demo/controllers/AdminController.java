package com.exilum.demo.controllers;

import com.exilum.demo.admin.UserManagementService;
import com.exilum.demo.model.DTO.CraftingMaterialDTO;
import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.DTO.ScarabDTO;
import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.CraftingMaterialRepository;
import com.exilum.demo.security.Permission;
import com.exilum.demo.security.Role;
import com.exilum.demo.service.fetching.CraftingMaterialFetchingService;
import com.exilum.demo.service.fetching.DeliriumOrbFetchingService;
import com.exilum.demo.service.fetching.MapFetchingService;
import com.exilum.demo.service.fetching.ScarabFetchingService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    ScarabFetchingService scarabFetchingService;
    @Autowired
    MapFetchingService mapFetchingService;
    @Autowired
    DeliriumOrbFetchingService deliriumOrbFetchingService;
    @Autowired
    CraftingMaterialFetchingService craftingMaterialFetchingService;
    @Autowired
    private UserManagementService userManagementService;

    // TODO: check if actually needed
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @PostMapping("/grantAdminRole/{uid}")
    public ResponseEntity<?> grantAdminRole(@PathVariable String uid) {
        try {
            String customToken = userManagementService.grantAdminRole(uid);
            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/grantAdminByEmail")
    public ResponseEntity<?> grantAdminRoleByEmail(@RequestParam String email) {
        try {
            String customToken = userManagementService.grantAdminRoleByEmail(email);
            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/revokeAdminByEmail")
    public ResponseEntity<?> revokeAdminRoleByEmail(@RequestParam String email) {
        try {
            String customToken = userManagementService.revokeAdminRoleByEmail(email);
            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/testScarabs")
    @ResponseBody
    public ScarabDTO[] testScarabs() {
        ScarabDTO[] result = scarabFetchingService.fetchScarabs();

        return result;
    }

    @GetMapping("/saveScarabs")
    public ResponseEntity<String> saveScarabs() {
        String msg = scarabFetchingService.fetchAndSaveScarabs();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/updateScarabs")
    public ResponseEntity<String> updateScarabs() {
        String msg = scarabFetchingService.updatePricesScarabs();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/testMaps")
    @ResponseBody
    public MapDTO[] testMaps() {
        MapDTO[] result = mapFetchingService.fetchMaps();

        return result;
    }

    @GetMapping("/saveMaps")
    public ResponseEntity<String> saveMaps() {
        String msg = mapFetchingService.fetchAndSaveMaps();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/updateMaps")
    public ResponseEntity<String> updateMaps() {
        String msg = mapFetchingService.updatePricesMaps();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/testDeliriumOrbs")
    @ResponseBody
    public DeliriumOrbDTO[] testDeliriumOrbs() {
        DeliriumOrbDTO[] result = deliriumOrbFetchingService.fetchDeliriumOrbs();

        return result;
    }

    @GetMapping("/saveDeliriumOrbs")
    public ResponseEntity<String> saveDeliriumOrbs() {
        String msg = deliriumOrbFetchingService.fetchAndSaveDeliriumOrbs();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/updateDeliriumOrbs")
    public ResponseEntity<String> updateDeliriumOrbs() {
        String msg = deliriumOrbFetchingService.updatePricesDeliriumOrbs();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/testCraftingMaterials")
    @ResponseBody
    public CraftingMaterialDTO[] testCraftingMaterials() {
        CraftingMaterialDTO[] result = craftingMaterialFetchingService.fetchCraftingMaterials();

        return result;
    }

    @GetMapping("/saveCraftingMaterials")
    public ResponseEntity<String> saveCraftingMaterials() {
        String msg = craftingMaterialFetchingService.fetchAndSaveCraftingMaterials();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/updateCraftingMaterials")
    public ResponseEntity<String> updateCraftingMaterials() {
        String msg = craftingMaterialFetchingService.updatePricesCraftingMaterials();
        return ResponseEntity.ok(msg);
    }

}
