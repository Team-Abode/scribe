package com.teamabode.scribe.client.model;

import com.teamabode.scribe.core.api.animation.AnimationManager;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public abstract class ScribeAnimatableModel<E extends Entity> extends HierarchicalModel<E> {

    public void animate(AnimationState animationState, ResourceLocation animation, float f) {
        this.animate(animationState, AnimationManager.createAnimation(animation), f);
    }

    protected void animate(AnimationState animationState, ResourceLocation animation, float f, float g) {
        this.animate(animationState, AnimationManager.createAnimation(animation), f, g);
    }

    protected void animateWalk(ResourceLocation animation, float f, float g, float h, float i) {
        this.animateWalk(AnimationManager.createAnimation(animation), f, g, h, i);
    }
}
