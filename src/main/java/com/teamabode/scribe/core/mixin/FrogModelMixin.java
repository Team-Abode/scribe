package com.teamabode.scribe.core.mixin;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.core.api.animation.AnimUtils;
import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FrogModel.class)
public class FrogModelMixin<T extends Frog> extends HierarchicalModel<T> {
    @Shadow @Final private ModelPart croakingBody;

    private static final ResourceLocation FROG_TONGUE = new ResourceLocation(Scribe.MOD_ID, "tongue");

    public void setupAnim(T frog, float f, float g, float h, float i, float j) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(frog.jumpAnimationState, FrogAnimation.FROG_JUMP, h);
        this.animate(frog.croakAnimationState, FrogAnimation.FROG_CROAK, h);
        this.animate(frog.tongueAnimationState, AnimUtils.getAnim(FROG_TONGUE), h);
        if (frog.isInWaterOrBubble()) {
            this.animateWalk(FrogAnimation.FROG_SWIM, f, g, 1.0F, 2.5F);
        } else {
            this.animateWalk(FrogAnimation.FROG_WALK, f, g, 1.5F, 2.5F);
        }

        this.animate(frog.swimIdleAnimationState, FrogAnimation.FROG_IDLE_WATER, h);
        this.croakingBody.visible = frog.croakAnimationState.isStarted();
    }

    @Shadow
    public ModelPart root() {
        throw new AssertionError();
    }
}
