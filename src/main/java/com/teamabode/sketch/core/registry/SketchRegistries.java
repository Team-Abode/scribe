package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;


public class SketchRegistries {

    public static final ResourceKey<Registry<SketchBoatType>> BOAT_TYPE = ResourceKey.createRegistryKey(new ResourceLocation(Sketch.MOD_ID, "boat_type"));

    public static void init() {

    }
}
