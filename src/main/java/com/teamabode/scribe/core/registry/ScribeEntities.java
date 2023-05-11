package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.Scribe;
import com.teamabode.scribe.common.entity.ScribeBoat;
import com.teamabode.scribe.common.entity.ScribeChestBoat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ScribeEntities {

    public static final EntityType<ScribeBoat> SCRIBE_BOAT = register("scribe_boat", EntityType.Builder.<ScribeBoat>of(ScribeBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
    public static final EntityType<ScribeChestBoat> SCRIBE_CHEST_BOAT = register("scribe_chest_boat", EntityType.Builder.<ScribeChestBoat>of(ScribeChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

    private static <E extends Entity> EntityType<E> register(String name, EntityType.Builder<E> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Scribe.MOD_ID, name), builder.build(name));
    }

    public static void init() {}
}
