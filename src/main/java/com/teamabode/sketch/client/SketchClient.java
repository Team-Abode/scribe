package com.teamabode.sketch.client;

import com.teamabode.sketch.client.renderer.SketchBoatRenderer;
import com.teamabode.sketch.core.api.animation.AnimationManager;
import com.teamabode.sketch.core.api.model.SketchModelPlugin;
import com.teamabode.sketch.core.registry.SketchEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.server.packs.PackType;

public class SketchClient implements ClientModInitializer {

    public void onInitializeClient() {
        EntityRendererRegistry.register(SketchEntities.SKETCH_BOAT, context -> new SketchBoatRenderer(context, false));
        EntityRendererRegistry.register(SketchEntities.SKETCH_CHEST_BOAT, context -> new SketchBoatRenderer(context, true));

        EntityModelLayerRegistry.registerModelLayer(SketchBoatRenderer.BOAT, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(SketchBoatRenderer.CHEST_BOAT, ChestBoatModel::createBodyModel);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(AnimationManager.INSTANCE);
        ModelLoadingPlugin.register(new SketchModelPlugin());
    }
}
