package com.teamabode.scribe.core.api.misc;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.Map;

public class BlockEntityAdditions {
    public static final Map<BlockEntityType<? extends BlockEntity>, List<Block>> ADDITIONS = Maps.newHashMap();

    public static <T extends BlockEntity> void appendBlocks(BlockEntityType<T> type, Block... blocks) {
        ADDITIONS.put(type, List.of(blocks));
    }
}
