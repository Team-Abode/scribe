package com.teamabode.sketch.core.mixin.client;

import com.teamabode.sketch.client.SketchClient;
import com.teamabode.sketch.core.api.animation.AnimationManager;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.FrogModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FrogModel.class)
public class FrogModelMixin {
    private static final ResourceLocation FROG_CROAK = new ResourceLocation("frog/croak");
    private static final ResourceLocation FROG_WALK = new ResourceLocation("frog/walk");
    private static final ResourceLocation FROG_JUMP = new ResourceLocation("frog/jump");
    private static final ResourceLocation FROG_TONGUE = new ResourceLocation("frog/tongue");
    private static final ResourceLocation FROG_SWIM = new ResourceLocation("frog/swim");
    private static final ResourceLocation FROG_IDLE_WATER = new ResourceLocation("frog/idle_water");

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 0), index = 1)
    private AnimationDefinition animateFrogJump(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_JUMP);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 1), index = 1)
    private AnimationDefinition animateFrogCroak(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_CROAK);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 2), index = 1)
    private AnimationDefinition animateFrogTongue(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_TONGUE);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 3), index = 1)
    private AnimationDefinition animateFrogIdleWater(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_IDLE_WATER);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animateWalk(Lnet/minecraft/client/animation/AnimationDefinition;FFFF)V", ordinal = 0), index = 0)
    private AnimationDefinition animateFrogSwim(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_SWIM);
    }

    @ModifyArg(method = "setupAnim(Lnet/minecraft/world/entity/animal/frog/Frog;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/FrogModel;animateWalk(Lnet/minecraft/client/animation/AnimationDefinition;FFFF)V", ordinal = 1), index = 0)
    private AnimationDefinition animateFrogWalk(AnimationDefinition originalParam) {
        return SketchClient.ANIMATION_MANAGER.getAnimation(FROG_WALK);
    }
}
