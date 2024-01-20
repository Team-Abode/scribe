package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;

public class SketchBuiltInRegistries {

    public static final Registry<SketchBoatType> BOAT_TYPE = FabricRegistryBuilder.createSimple(SketchRegistries.BOAT_TYPE).buildAndRegister();

    public static void init() {

    }
}
