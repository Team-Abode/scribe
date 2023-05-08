package com.teamabode.scribe.core.api.animation;

import com.google.gson.JsonParser;
import com.teamabode.scribe.Scribe;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class AnimationManager extends SimplePreparableReloadListener<Map<ResourceLocation, AnimationData>> implements IdentifiableResourceReloadListener {
    public static final Map<ResourceLocation, AnimationData> ANIMATIONS = new HashMap<>();

    protected @NotNull Map<ResourceLocation, AnimationData> prepare(ResourceManager manager, ProfilerFiller filler) {
        Map<ResourceLocation, AnimationData> animations = new HashMap<>();

        for (var resourceEntry : manager.listResources("scribe_animations", location -> location.getPath().endsWith(".json")).entrySet()) {
            ResourceLocation location = resourceEntry.getKey();
            Resource resource = resourceEntry.getValue();

            try (BufferedReader reader = resource.openAsReader()) {
                ResourceLocation parsedLocation = new ResourceLocation(location.getNamespace(), location.getPath().substring(18, location.getPath().length() - 5));
                animations.put(parsedLocation, new AnimationData(JsonParser.parseReader(reader)));
            } catch (Exception io) {
                Scribe.LOGGER.error("Couldn't parse animation {}", location, io);
            }
        }
        return animations;
    }

    protected void apply(Map<ResourceLocation, AnimationData> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ANIMATIONS.clear();
        ANIMATIONS.putAll(object);
    }

    public ResourceLocation getFabricId() {
        return new ResourceLocation(Scribe.MOD_ID, "animation_manager");
    }
}
