package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoat;
import com.teamabode.sketch.common.entity.boat.SketchChestBoat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SketchEntities {

    public static final EntityType<SketchBoat> SKETCH_BOAT = register("boat", EntityType.Builder.<SketchBoat>of(SketchBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
    public static final EntityType<SketchChestBoat> SKETCH_CHEST_BOAT = register("chest_boat", EntityType.Builder.<SketchChestBoat>of(SketchChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

    private static <E extends Entity> EntityType<E> register(String name, EntityType.Builder<E> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Sketch.MOD_ID, name), builder.build(name));
    }

    public static void init() {}
}
