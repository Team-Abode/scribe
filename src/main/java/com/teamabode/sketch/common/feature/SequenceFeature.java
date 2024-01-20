package com.teamabode.sketch.common.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SequenceFeature extends Feature<SequenceFeatureConfiguration> {

    public SequenceFeature() {
        super(SequenceFeatureConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<SequenceFeatureConfiguration> context) {
        SequenceFeatureConfiguration config = context.config();
        WorldGenLevel level = context.level();
        ChunkGenerator chunkGen = context.chunkGenerator();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        int successes = 0;
        for (Holder<PlacedFeature> feature : config.placedFeatures()) {
            if (feature.value().place(level, chunkGen, random, origin)) successes++;
        }
        return successes > 0;
    }
}
