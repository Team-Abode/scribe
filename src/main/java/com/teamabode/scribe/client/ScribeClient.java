package com.teamabode.scribe.client;

import com.teamabode.scribe.client.renderer.ScribeBoatRenderer;
import com.teamabode.scribe.core.registry.ScribeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;

public class ScribeClient implements ClientModInitializer {

    public void onInitializeClient() {
        EntityRendererRegistry.register(ScribeEntities.SCRIBE_BOAT, context -> new ScribeBoatRenderer(context, false));
        EntityRendererRegistry.register(ScribeEntities.SCRIBE_CHEST_BOAT, context -> new ScribeBoatRenderer(context, true));

        EntityModelLayerRegistry.registerModelLayer(ScribeBoatRenderer.BOAT, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(ScribeBoatRenderer.CHEST_BOAT, ChestBoatModel::createBodyModel);
    }
}
