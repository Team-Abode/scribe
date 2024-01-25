package com.teamabode.sketch.core.mixin;

import com.teamabode.sketch.core.api.misc.BlockEntityAdditions;
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
        var $this = BlockEntityType.class.cast(this);
        BlockEntityAdditions.ADDITIONS.get($this).forEach(block -> cir.setReturnValue(true));
    }
}
