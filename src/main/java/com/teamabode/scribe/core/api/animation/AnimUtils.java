package com.teamabode.scribe.core.api.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.resources.ResourceLocation;

public class AnimUtils {

    public static AnimationDefinition getAnim(ResourceLocation resourceLocation) {
        return AnimationParser.createAnimation(resourceLocation);
    }
}
