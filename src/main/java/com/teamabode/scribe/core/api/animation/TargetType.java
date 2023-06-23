package com.teamabode.scribe.core.api.animation;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum TargetType implements StringRepresentable {
    POSITION("position"),
    ROTATION("rotation"),
    SCALE("scale");

    public static final Codec<TargetType> CODEC = StringRepresentable.fromEnum(TargetType::values);

    private String name;

    TargetType(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}