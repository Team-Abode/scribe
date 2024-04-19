package com.teamabode.sketch.core.mixin;

import com.teamabode.sketch.core.api.event.ShieldEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "blockUsingShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;disableShield(Z)V", shift = At.Shift.AFTER))
    private void sketch$blockUsingShield(LivingEntity attacker, CallbackInfo ci) {
        ShieldEvents.DISABLED.invoker().disabled((Player) (Object) this, attacker);
    }
}
