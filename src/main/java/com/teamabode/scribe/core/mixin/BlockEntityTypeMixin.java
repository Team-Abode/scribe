package com.teamabode.scribe.core.mixin;

import com.teamabode.scribe.core.api.misc.BlockEntityAdditions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void isValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var thisType = BlockEntityType.class.cast(this);
        BlockEntityAdditions.ADDITIONS.forEach((type, blocks) -> blocks.forEach(block -> {
            if (thisType == type && state.getBlock() == block) cir.setReturnValue(true);
        }));
    }
}
