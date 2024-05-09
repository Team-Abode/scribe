package com.teamabode.sketch.core.api.model;

import com.teamabode.sketch.core.mixin.client.BlockModelAccessor;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.resources.model.UnbakedModel;

import java.util.List;
import java.util.stream.Stream;

public class SketchModelPlugin implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context context) {
        context.modifyModelOnLoad().register(SketchModelPlugin::injectItemOverrides);
    }

    private static UnbakedModel injectItemOverrides(UnbakedModel model, ModelModifier.OnLoad.Context context) {
        var registry = ModelOverrideManager.INSTANCE.getRegistry();
        for (var entry : registry.entrySet()) {
            boolean locationsMatch = entry.getKey().equals(context.id());
            if (locationsMatch && model instanceof BlockModel blockModel) {
                BlockModelAccessor accessor = (BlockModelAccessor) blockModel;
                List<ItemOverride> overrides = entry.getValue();
                List<ItemOverride> oldOverrides = blockModel.getOverrides();
                Stream<ItemOverride> merged = Stream.concat(oldOverrides.stream(), overrides.stream());
                accessor.setOverrides(merged.toList());
                return blockModel;
            }
        }
        return model;
    }
}
