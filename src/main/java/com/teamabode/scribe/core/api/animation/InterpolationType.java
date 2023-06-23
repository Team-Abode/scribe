package com.teamabode.scribe.core.api.animation;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum InterpolationType implements StringRepresentable {
    LINEAR("linear"),
    CATMULLROM("catmullrom");

    public static final Codec<InterpolationType> CODEC = StringRepresentable.fromEnum(InterpolationType::values);

    private final String name;

    InterpolationType(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}