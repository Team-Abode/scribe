package com.teamabode.sketch.client.model;

import com.teamabode.sketch.client.SketchClient;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public abstract class SketchAnimatableModel<E extends Entity> extends HierarchicalModel<E> {

    public void animate(AnimationState animationState, ResourceLocation animation, float f) {
        this.animate(animationState, SketchClient.ANIMATION_MANAGER.getAnimation(animation), f);
    }

    protected void animate(AnimationState animationState, ResourceLocation animation, float f, float g) {
        this.animate(animationState, SketchClient.ANIMATION_MANAGER.getAnimation(animation), f, g);
    }

    protected void animateWalk(ResourceLocation animation, float f, float g, float h, float i) {
        this.animateWalk(SketchClient.ANIMATION_MANAGER.getAnimation(animation), f, g, h, i);
    }

    @Override
    protected final void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks) {
        super.animate(animationState, animationDefinition, ageInTicks);
    }

    @Override
    protected final void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed) {
        super.animate(animationState, animationDefinition, ageInTicks, speed);
    }

    @Override
    protected final void animateWalk(AnimationDefinition animationDefinition, float limbSwing, float limbSwingAmount, float maxAnimationSpeed, float animationScaleFactor) {
        super.animateWalk(animationDefinition, limbSwing, limbSwingAmount, maxAnimationSpeed, animationScaleFactor);
    }
}
