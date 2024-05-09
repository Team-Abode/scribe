package com.teamabode.sketch.core.api.model;

import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.List;

public class ModelOverride {
    private final ResourceLocation model;
    private final List<ItemOverride> overrides;

    public ModelOverride(ResourceLocation item, List<ItemOverride> overrides) {
        this.model = item;
        this.overrides = overrides;
    }

    public ResourceLocation getModel() {
        return this.model;
    }

    public List<ItemOverride> getOverrides() {
        return this.overrides;
    }

    public static class Deserializer implements JsonDeserializer<ModelOverride> {
        @Override
        public ModelOverride deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();


            String modelId = GsonHelper.getAsString(root, "model", "");
            List<ItemOverride> overrides = this.getOverrides(context, root);

            return new ModelOverride(ResourceLocation.tryParse(modelId), overrides);
        }

        private List<ItemOverride> getOverrides(JsonDeserializationContext context, JsonObject root) {
            List<ItemOverride> overrides = Lists.newArrayList();
            if (root.has("overrides")) {
                JsonArray overridesJson = GsonHelper.getAsJsonArray(root, "overrides");

                for (JsonElement overrideJson : overridesJson) {
                    overrides.add(context.deserialize(overrideJson, ItemOverride.class));
                }
            }
            return overrides;
        }
    }
}
