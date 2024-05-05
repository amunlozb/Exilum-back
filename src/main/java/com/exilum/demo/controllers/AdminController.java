package com.exilum.demo.controllers;

import com.exilum.demo.admin.UserManagementService;
import com.exilum.demo.model.DTO.CraftingMaterialDTO;
import com.exilum.demo.model.DTO.DeliriumOrbDTO;
import com.exilum.demo.model.DTO.MapDTO;
import com.exilum.demo.model.DTO.ScarabDTO;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    // TODO: check if actually needed
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private final UserManagementService userManagementService;

    // TODO: add return msg
    @PostMapping(path = "/roles/{uid}")
    public String setRolesUserClaims(
            @PathVariable String uid,
            @RequestBody Role requestedRole
    ) throws FirebaseAuthException {
        try {
            return(userManagementService.setRolesNew(uid, requestedRole));
        } catch (FirebaseAuthException e) {
            return e.getMessage();
        }

    }

    @GetMapping("/testScarabs")
    @ResponseBody
    public ScarabDTO[] testScarabs() {
        ScarabDTO[] result = scarabFetchingService.fetchScarabs();

        return result;
    }

    @GetMapping("/testMaps")
    @ResponseBody
    public MapDTO[] testMaps() {
        MapDTO[] result = mapFetchingService.fetchMaps();

        return result;
    }
    @GetMapping("/testDeliriumOrbs")
    @ResponseBody
    public DeliriumOrbDTO[] testDeliriumOrbs() {
        DeliriumOrbDTO[] result = deliriumOrbFetchingService.fetchDeliriumOrbs();

        return result;
    }
    @GetMapping("/testCraftingMaterials")
    @ResponseBody
    public CraftingMaterialDTO[] testCraftingMaterials() {
        CraftingMaterialDTO[] result = craftingMaterialFetchingService.fetchCraftingMaterials();

        return result;
    }

}
