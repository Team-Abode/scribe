package com.teamabode.sketch.core.api.misc;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockEntityExtender {
    public static final ArrayList<Entry> REGISTRY = Lists.newArrayList();

    public static <T extends BlockEntity> void addValidBlocks(BlockEntityType<T> type, Block... validBlocks) {
        REGISTRY.add(new Entry(type, Set.of(validBlocks)));
    }

    public static boolean canExtend(BlockEntityType<?> type, BlockState state) {
        if (REGISTRY.isEmpty()) {
            return false;
        }
        for (BlockEntityExtender.Entry entry : REGISTRY) {
            if (entry.validBlocks().contains(state.getBlock())) {
                return type == entry.type();
            }
        }
        return false;
    }

    public record Entry(BlockEntityType<? extends BlockEntity> type, Set<Block> validBlocks) {
        public Entry(BlockEntityType<? extends BlockEntity> type, Set<Block> validBlocks) {
            this.type = type;
            this.validBlocks = validBlocks;
        }
    }
}
