package com.teamabode.sketch.core.api.animation;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.Map;

public record AnimationHolder(float lengthInSeconds, boolean looping, Map<String, Map<TargetType, Map<String, KeyframeHolder>>> bones) {

    public static final Codec<String> TIMESTAMP_CODEC = Codec.STRING.validate(value -> {
        try {
            float parsedValue = Float.parseFloat(value);
            return DataResult.success(Float.toString(parsedValue));
        }
        catch (NumberFormatException exception) {
            return DataResult.error(() -> "Timestamp must be a float: " + value);
        }
    });

    public static final Codec<AnimationHolder> CODEC = RecordCodecBuilder.create(instance -> {
        var length = ExtraCodecs.POSITIVE_FLOAT.fieldOf("length_in_seconds").forGetter(AnimationHolder::lengthInSeconds);
        var looping = Codec.BOOL.fieldOf("looping").forGetter(AnimationHolder::looping);
        var timestamps = Codec.unboundedMap(TIMESTAMP_CODEC, KeyframeHolder.CODEC);
        var targets = Codec.simpleMap(TargetType.CODEC, timestamps, StringRepresentable.keys(TargetType.values()));
        var bones = Codec.unboundedMap(Codec.STRING, targets.codec()).fieldOf("bones").forGetter(AnimationHolder::bones);

        var group = instance.group(length, looping, bones);
        return group.apply(instance, AnimationHolder::new);
    });

    public AnimationHolder(float lengthInSeconds, boolean looping, Map<String, Map<TargetType, Map<String, KeyframeHolder>>> bones) {
        this.lengthInSeconds = lengthInSeconds;
        this.looping = looping;
        this.bones = ImmutableMap.copyOf(bones);
    }
}
