package com.teamabode.sketch.core.mixin.client;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BlockModel.class)
public interface BlockModelAccessor {
    @Accessor("overrides")
    void setOverrides(List<ItemOverride> overrides);
}
