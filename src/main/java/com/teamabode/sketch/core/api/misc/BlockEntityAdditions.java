package com.teamabode.sketch.core.api.misc;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockEntityAdditions {
    public static final Map<BlockEntityType<? extends BlockEntity>, Set<Block>> ADDITIONS = Maps.newHashMap();

    public static <T extends BlockEntity> void appendBlocks(BlockEntityType<T> type, Block... blocks) {
        ADDITIONS.put(type, Set.of(blocks));
    }
}
