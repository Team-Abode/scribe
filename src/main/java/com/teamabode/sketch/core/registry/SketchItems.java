package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import com.teamabode.sketch.common.item.SketchBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class SketchItems {
    public static final Item FALLBACK_BOAT = new SketchBoatItem(new Item.Properties().stacksTo(1), SketchBoatType.FALLBACK, false);
    public static final Item FALLBACK_CHEST_BOAT = new SketchBoatItem(new Item.Properties().stacksTo(1), SketchBoatType.FALLBACK, true);

    public static void init() {
        Registry.register(BuiltInRegistries.ITEM, Sketch.id("fallback_boat"), FALLBACK_BOAT);
        Registry.register(BuiltInRegistries.ITEM, Sketch.id("fallback_chest_boat"), FALLBACK_CHEST_BOAT);
    }
}
