package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import com.teamabode.sketch.common.item.SketchBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class SketchItems {

    public static final Item DEFAULT_BOAT = new SketchBoatItem(new Item.Properties().stacksTo(1), SketchBoatType.DEFAULT, false);
    public static final Item DEFAULT_CHEST_BOAT = new SketchBoatItem(new Item.Properties().stacksTo(1), SketchBoatType.DEFAULT, true);

    public static void init() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Sketch.MOD_ID, "default_boat"), DEFAULT_BOAT);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Sketch.MOD_ID, "default_chest_boat"), DEFAULT_CHEST_BOAT);
    }
}
