package com.teamabode.scribe.core.api.animation;

import com.google.common.collect.Maps;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.teamabode.scribe.Scribe;
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

public class AnimationManager extends SimplePreparableReloadListener<Map<ResourceLocation, AnimationHolder>> implements IdentifiableResourceReloadListener {
    private static final Map<ResourceLocation, AnimationHolder> ANIMATIONS = Maps.newHashMap();

    protected Map<ResourceLocation, AnimationHolder> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, AnimationHolder> registry = Maps.newHashMap();

        for (var resourceEntry : resourceManager.listResources("animations", location -> location.getPath().endsWith(".json")).entrySet()) {
            ResourceLocation location = resourceEntry.getKey();
            String path = location.getPath();

            ResourceLocation trueLocation = new ResourceLocation(location.getNamespace(), path.substring(11, path.length() - 5));
            Resource resource = resourceEntry.getValue();

            try (BufferedReader reader = resource.openAsReader()) {
                var result = AnimationHolder.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader));

                if (result.error().isPresent()) {
                    throw new JsonParseException(result.error().get().message());
                }
                if (registry.put(trueLocation, result.result().get().getFirst()) != null) {
                    Scribe.LOGGER.warn("Duplicate animation found: {}", trueLocation);
                }
            }
            catch (Exception exception) {
                Scribe.LOGGER.warn("Animation could not load: {}", resourceEntry.getKey(), exception);
            }
        }
        return registry;
    }

    protected void apply(Map<ResourceLocation, AnimationHolder> registry, ResourceManager resourceManager, ProfilerFiller profiler) {
        ANIMATIONS.clear();
        ANIMATIONS.putAll(registry);
    }

    public static AnimationDefinition createAnimation(ResourceLocation location) {
        AnimationHolder anim = ANIMATIONS.get(location);
        if (anim == null) return nullAnimation(location);

        Builder builder = Builder.withLength(anim.length());
        var bones = anim.bones();
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
                builder.addAnimation(bone, new AnimationChannel(ParseUtils.getTarget(target), keyframes));
            });
        });
        if (anim.looping()) builder.looping();
        return builder.build();
    }

    private static AnimationDefinition nullAnimation(ResourceLocation resourceLocation) {
        Scribe.LOGGER.warn("Animation is null: {}", resourceLocation.toString());
        return AnimationDefinition.Builder.withLength(0.0F).build();
    }

    public ResourceLocation getFabricId() {
        return new ResourceLocation(Scribe.MOD_ID, "animation_manager");
    }
}
