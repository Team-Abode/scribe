package com.teamabode.scribe.common.entity;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.core.registry.ScribeBuiltInRegistries;
import com.teamabode.scribe.core.registry.ScribeItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record ScribeBoatType(String name, Item boatItem, Item chestBoatItem) {
    public static final ScribeBoatType DEFAULT = register("default", ScribeItems.DEFAULT_BOAT, ScribeItems.DEFAULT_CHEST_BOAT);

    public static void init() {}

    public ScribeBoatType(String name, Item boatItem, Item chestBoatItem) {
        this.name = name;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
    }

    private static ScribeBoatType register(String name, Item dropItem, Item chestItem) {
        return Registry.register(ScribeBuiltInRegistries.BOAT_TYPE, new ResourceLocation(Scribe.MOD_ID, name), new ScribeBoatType(name, dropItem, chestItem));
    }
}
