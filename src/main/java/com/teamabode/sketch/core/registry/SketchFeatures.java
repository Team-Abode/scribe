package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.feature.SequenceFeature;
import com.teamabode.sketch.common.feature.SequenceFeatureConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SketchFeatures {

    public static final Feature<SequenceFeatureConfiguration> SEQUENCE = create("sequence", new SequenceFeature());

    private static <FC extends FeatureConfiguration> Feature<FC> create(String name, Feature<FC> feature) {
        return Registry.register(BuiltInRegistries.FEATURE, Sketch.id(name), feature);
    }

    public static void init() {}
}
