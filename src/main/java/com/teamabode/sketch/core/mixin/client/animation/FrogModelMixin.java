package com.teamabode.sketch.core.mixin.client.animation;

import com.teamabode.sketch.core.api.animation.AnimationManager;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.FrogModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FrogModel.class)
public class FrogModelMixin {
    private static final ResourceLocation FROG_CROAK = ResourceLocation.withDefaultNamespace("frog/croak");
    private static final ResourceLocation FROG_WALK = ResourceLocation.withDefaultNamespace("frog/walk");
    private static final ResourceLocation FROG_JUMP = ResourceLocation.withDefaultNamespace("frog/jump");
    private static final ResourceLocation FROG_TONGUE = ResourceLocation.withDefaultNamespace("frog/tongue");
    private static final ResourceLocation FROG_SWIM = ResourceLocation.withDefaultNamespace("frog/swim");
    private static final ResourceLocation FROG_IDLE_WATER = ResourceLocation.withDefaultNamespace("frog/idle_water");

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 0), index = 1)
    private AnimationDefinition animateFrogJump(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_JUMP);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 1), index = 1)
    private AnimationDefinition animateFrogCroak(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_CROAK);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 2), index = 1)
    private AnimationDefinition animateFrogTongue(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_TONGUE);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 3), index = 1)
    private AnimationDefinition animateFrogIdleWater(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_IDLE_WATER);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animateWalk(Lnet/minecraft/client/animation/AnimationDefinition;FFFF)V", ordinal = 0), index = 0)
    private AnimationDefinition animateFrogSwim(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_SWIM);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animateWalk(Lnet/minecraft/client/animation/AnimationDefinition;FFFF)V", ordinal = 1), index = 0)
    private AnimationDefinition animateFrogWalk(AnimationDefinition originalParam) {
        return AnimationManager.INSTANCE.getAnimation(FROG_WALK);
    }
}
