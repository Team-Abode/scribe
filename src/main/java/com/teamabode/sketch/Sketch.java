package com.teamabode.sketch;

import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import com.teamabode.sketch.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sketch implements ModInitializer {
    public static final String MOD_ID = "sketch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        LOGGER.info("Initializing Sketch");
        SketchRegistries.init();
        SketchDataSerializers.init();
        SketchFeatures.init();
        SketchBoatType.init();
        SketchEntities.init();
        DynamicRegistries.registerSynced(SketchRegistries.BOAT_TYPE, SketchBoatType.CODEC);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
