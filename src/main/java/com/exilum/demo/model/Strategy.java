package com.exilum.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Strategies")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_id")
    private Integer strategyId;

    @ManyToMany
    @JoinTable(
            name = "Strategies_have_Scarabs",
            joinColumns = @JoinColumn(name = "strategy_id"),
            inverseJoinColumns = @JoinColumn(name = "scarab_id")
    )
    private List<Scarab> scarabs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Strategies_have_DeliriumOrbs",
            joinColumns = @JoinColumn(name = "strategy_id"),
            inverseJoinColumns = @JoinColumn(name = "delirium_id")
    )
    private List<DeliriumOrb> deliriumOrbs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Strategies_have_CraftingMaterials",
            joinColumns = @JoinColumn(name = "strategy_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<CraftingMaterial> craftingMaterials = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    @ManyToOne
    @JoinColumn(name = "craft_id")
    private DeviceCraft craft;

    public void addScarab(Scarab scarab, int quantity) {
        for (int i = 0; i < quantity; i++) {
            scarabs.add(scarab);
        }
    }

    public void addDeliriumOrb(DeliriumOrb deliriumOrb, int quantity) {
        for (int i = 0; i < quantity; i++) {
            deliriumOrbs.add(deliriumOrb);
        }
    }

    public void addCraftingMaterial(CraftingMaterial craftingMaterial, int quantity) {
        for (int i = 0; i < quantity; i++) {
            craftingMaterials.add(craftingMaterial);
        }
    }

    public void addDeviceCraft(DeviceCraft deviceCraft, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.craft = deviceCraft;
        }
    }

    public void addMap(Map map, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.map = map;
        }
    }
}
