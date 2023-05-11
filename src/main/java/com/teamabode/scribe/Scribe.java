package com.teamabode.scribe;

import com.teamabode.scribe.common.entity.ScribeBoatType;
import com.teamabode.scribe.core.api.animation.AnimationManager;
import com.teamabode.scribe.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scribe implements ModInitializer {
    public static final String MOD_ID = "scribe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final AnimationManager ANIMATION_MANAGER = new AnimationManager();

    public void onInitialize() {
        LOGGER.info("Initializing Scribe...");

        ScribeRegistries.init();
        ScribeBuiltInRegistries.init();
        ScribeDataSerializers.init();

        ScribeFeatures.init();
        ScribeItems.init();
        ScribeBoatType.init();
        ScribeEntities.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.OP_BLOCKS).register(entries -> {
            if (entries.getContext().hasPermissions()) {
                entries.addAfter(Items.DEBUG_STICK, ScribeItems.DEFAULT_BOAT, ScribeItems.DEFAULT_CHEST_BOAT);
            }
        });

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ANIMATION_MANAGER);
    }
}
