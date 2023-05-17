package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;


public class ScribeRegistries {

    public static final ResourceKey<Registry<ScribeBoatType>> BOAT_TYPE = ResourceKey.createRegistryKey(new ResourceLocation(Scribe.MOD_ID, "boat_type"));

    public static void init() {

    }
}
