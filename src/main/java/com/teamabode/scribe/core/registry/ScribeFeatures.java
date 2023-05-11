package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.common.feature.SequenceFeature;
import com.teamabode.scribe.common.feature.SequenceFeatureConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ScribeFeatures {

    public static final Feature<SequenceFeatureConfiguration> SEQUENCE = create("sequence", new SequenceFeature());

    private static <FC extends FeatureConfiguration> Feature<FC> create(String name, Feature<FC> feature) {
        return Registry.register(BuiltInRegistries.FEATURE, new ResourceLocation(Scribe.MOD_ID, name), feature);
    }

    public static void init() {}
}
