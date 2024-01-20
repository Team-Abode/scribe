package com.teamabode.sketch.core.api.animation;

import com.google.common.collect.Maps;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.teamabode.sketch.Sketch;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimationManager extends SimplePreparableReloadListener<Map<ResourceLocation, AnimationDefinition>> implements IdentifiableResourceReloadListener {
    private static final Map<ResourceLocation, AnimationDefinition> ANIMATIONS = Maps.newHashMap();

    protected Map<ResourceLocation, AnimationDefinition> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, AnimationDefinition> registry = Maps.newHashMap();

        for (var resourceEntry : resourceManager.listResources("scribe_animations", location -> location.getPath().endsWith(".json")).entrySet()) {
            ResourceLocation location = resourceEntry.getKey();
            String path = location.getPath();

            ResourceLocation trueLocation = new ResourceLocation(location.getNamespace(), path.substring(18, path.length() - 5));
            Resource resource = resourceEntry.getValue();

            try (BufferedReader reader = resource.openAsReader()) {
                var result = AnimationHolder.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader));

                if (result.error().isPresent()) {
                    throw new JsonParseException(result.error().get().message());
                }
                if (registry.put(trueLocation, createAnimation(result.result().get().getFirst())) != null) {
                    Sketch.LOGGER.warn("Duplicate animation found: {}", trueLocation);
                }
            }
            catch (Exception exception) {
                Sketch.LOGGER.warn("Animation could not load: {}", resourceEntry.getKey(), exception);
            }
        }
        return registry;
    }

    protected void apply(Map<ResourceLocation, AnimationDefinition> registry, ResourceManager resourceManager, ProfilerFiller profiler) {
        ANIMATIONS.clear();
        ANIMATIONS.putAll(registry);
    }

    public static AnimationDefinition getAnimation(ResourceLocation id) {
        return ANIMATIONS.get(id);
    }

    private static AnimationDefinition createAnimation(AnimationHolder data) {
        Builder builder = Builder.withLength(data.length());
        var bones = data.bones();
        bones.forEach((bone, targetMap) -> {
            var targets = bones.get(bone);
            targets.forEach((target, timestampMap) -> {
                var timestamps = targets.get(target);
                Keyframe[] keyframes = new Keyframe[timestampMap.size()];
                AtomicInteger current = new AtomicInteger(0);

                timestamps.forEach((timestamp, keyframe) -> {
                    keyframes[current.get()] = new Keyframe(Float.parseFloat(timestamp), ParseUtils.getTargetVec(keyframe.transformation(), target), ParseUtils.getInterpolation(keyframe.interpolation()));
                    current.addAndGet(1);
                });
                builder.addAnimation(bone, new AnimationChannel(target.getTarget(), keyframes));
            });
        });
        if (data.looping()) builder.looping();
        return builder.build();
    }

    public ResourceLocation getFabricId() {
        return new ResourceLocation(Sketch.MOD_ID, "animation_manager");
    }
}
