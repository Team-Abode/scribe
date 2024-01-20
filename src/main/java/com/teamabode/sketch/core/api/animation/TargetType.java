package com.teamabode.sketch.core.api.animation;

import com.mojang.serialization.Codec;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.util.StringRepresentable;

public enum TargetType implements StringRepresentable {
    POSITION("position", AnimationChannel.Targets.POSITION),
    ROTATION("rotation", AnimationChannel.Targets.ROTATION),
    SCALE("scale", AnimationChannel.Targets.SCALE);

    public static final Codec<TargetType> CODEC = StringRepresentable.fromEnum(TargetType::values);

    private final String name;
    private final AnimationChannel.Target target;

    TargetType(String name, AnimationChannel.Target target) {
        this.name = name;
        this.target = target;
    }

    public AnimationChannel.Target getTarget() {
        return this.target;
    }

    public String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}