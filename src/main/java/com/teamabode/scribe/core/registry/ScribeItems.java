package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import com.teamabode.scribe.common.item.ScribeBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ScribeItems {

    public static final Item DEFAULT_BOAT = new ScribeBoatItem(new Item.Properties().stacksTo(1), ScribeBoatType.DEFAULT, false);
    public static final Item DEFAULT_CHEST_BOAT = new ScribeBoatItem(new Item.Properties().stacksTo(1), ScribeBoatType.DEFAULT, true);

    public static void init() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Scribe.MOD_ID, "default_boat"), DEFAULT_BOAT);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Scribe.MOD_ID, "default_chest_boat"), DEFAULT_CHEST_BOAT);
    }
}
