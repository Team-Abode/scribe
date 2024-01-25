package com.teamabode.sketch.core.api.advancement;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;

public class AdvancementEvents {

    public static final Event<Modify> MODIFY = EventFactory.createArrayBacked(Modify.class, listeners -> (resourceManager, advancementManager, id, builder) -> {
        for (Modify listener : listeners) {
            listener.modifyAdvancement(resourceManager, advancementManager, id, builder);
        }
    });

    public interface Modify {
        void modifyAdvancement(ResourceManager resourceManager, ServerAdvancementManager advancementManager, ResourceLocation id, Advancement.Builder builder);
    }
}
