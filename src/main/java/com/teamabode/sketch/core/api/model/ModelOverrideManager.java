package com.teamabode.sketch.core.api.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamabode.sketch.Sketch;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModelOverrideManager {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ModelOverride.class, new ModelOverride.Deserializer())
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer())
            .create();
    public static final ModelOverrideManager INSTANCE = new ModelOverrideManager();
    private static final FileToIdConverter MODEL_OVERRIDE_LISTER = FileToIdConverter.json("sketch/model_overrides");

    private Map<ResourceLocation, List<ItemOverride>> modelOverrides;

    public ModelOverrideManager() {
        this.modelOverrides = Map.of();
    }

    public void loadModelOverrides(ResourceManager manager) {
        Map<ResourceLocation, List<ItemOverride>> registry = Maps.newHashMap();

        CompletableFuture.runAsync(() -> MODEL_OVERRIDE_LISTER.listMatchingResourceStacks(manager).forEach((location, resources) -> {
            resources.forEach(resource -> {
                try (BufferedReader reader = resource.openAsReader()) {
                    ModelOverride modelOverride = GsonHelper.fromJson(GSON, reader, ModelOverride.class);
                    ResourceLocation item = modelOverride.getModel();
                    List<ItemOverride> overrides = modelOverride.getOverrides();

                    registry.computeIfPresent(
                            item,
                            (id, oldOverrides) -> Stream.concat(oldOverrides.stream(), overrides.stream()).toList()
                    );
                    registry.put(item, overrides);
                } catch (Exception e) {
                    Sketch.LOGGER.error("Failed to parse model override {}: {}", location, e);
                }
            });
            this.modelOverrides = ImmutableMap.copyOf(registry);
        }));
    }

    public Map<ResourceLocation, List<ItemOverride>> getRegistry() {
        return this.modelOverrides;
    }

    public List<ItemOverride> get(ResourceLocation model) {
        return this.modelOverrides.get(model);
    }
}
