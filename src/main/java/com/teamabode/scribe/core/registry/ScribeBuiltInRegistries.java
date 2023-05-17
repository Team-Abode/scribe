package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;

public class ScribeBuiltInRegistries {

    public static final Registry<ScribeBoatType> BOAT_TYPE = FabricRegistryBuilder.createSimple(ScribeRegistries.BOAT_TYPE).buildAndRegister();

    public static void init() {

    }
}
