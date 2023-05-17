package com.teamabode.scribe.common.entity.boat;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.core.registry.ScribeBuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record ScribeBoatType(String name) {
    public static final ScribeBoatType DEFAULT = register("default");

    public static void init() {}

    public ScribeBoatType(String name) {
        this.name = name;
    }

    private static ScribeBoatType register(String name) {
        return Registry.register(ScribeBuiltInRegistries.BOAT_TYPE, new ResourceLocation(Scribe.MOD_ID, name), new ScribeBoatType(name));
    }
}
