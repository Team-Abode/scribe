package com.teamabode.scribe.client;

import com.teamabode.scribe.client.renderer.ScribeBoatRenderer;
import com.teamabode.scribe.core.api.animation.AnimationManager;
import com.teamabode.scribe.core.registry.ScribeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.server.packs.PackType;

public class ScribeClient implements ClientModInitializer {
    public static final AnimationManager ANIMATION_MANAGER = new AnimationManager();

    public void onInitializeClient() {
        EntityRendererRegistry.register(ScribeEntities.SCRIBE_BOAT, context -> new ScribeBoatRenderer(context, false));
        EntityRendererRegistry.register(ScribeEntities.SCRIBE_CHEST_BOAT, context -> new ScribeBoatRenderer(context, true));

        EntityModelLayerRegistry.registerModelLayer(ScribeBoatRenderer.BOAT, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(ScribeBoatRenderer.CHEST_BOAT, ChestBoatModel::createBodyModel);

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ANIMATION_MANAGER);
    }
}
