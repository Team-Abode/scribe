package com.teamabode.scribe;

import com.teamabode.scribe.common.entity.boat.ScribeBoatDispenseItemBehavior;
import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import com.teamabode.scribe.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scribe implements ModInitializer {
    public static final String MOD_ID = "scribe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
        DispenserBlock.registerBehavior(ScribeItems.DEFAULT_BOAT, new ScribeBoatDispenseItemBehavior(ScribeBoatType.DEFAULT));
        DispenserBlock.registerBehavior(ScribeItems.DEFAULT_CHEST_BOAT, new ScribeBoatDispenseItemBehavior(ScribeBoatType.DEFAULT, true));
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
