package com.teamabode.sketch.core.mixin;

import com.teamabode.sketch.core.api.misc.BlockEntityAdditions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "isValid", at = @At("RETURN"), cancellable = true)
    private void isValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        BlockEntityType<?> $this = BlockEntityType.class.cast(this);

        if (BlockEntityAdditions.ADDITIONS.containsKey($this)) {
            boolean hasBlock = BlockEntityAdditions.ADDITIONS.get($this).contains(state.getBlock());
            if (hasBlock) {
                cir.setReturnValue(hasBlock);
            }
        }
    }
}
