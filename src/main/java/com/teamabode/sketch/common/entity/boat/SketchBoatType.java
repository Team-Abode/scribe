package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record SketchBoatType(String name) {
    public static final SketchBoatType FALLBACK = register("fallback");

    public static void init() {}

    public SketchBoatType(String name) {
        this.name = name;
    }

    private static SketchBoatType register(String name) {
        return Registry.register(SketchBuiltInRegistries.BOAT_TYPE, new ResourceLocation(Sketch.MOD_ID, name), new SketchBoatType(name));
    }
}
