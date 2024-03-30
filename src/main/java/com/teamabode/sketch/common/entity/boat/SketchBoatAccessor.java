package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public interface SketchBoatAccessor {
    void setBoatType(SketchBoatType type);

    SketchBoatType getBoatType();

    default void addSaveData(CompoundTag tag) {
        ResourceLocation location = SketchBuiltInRegistries.BOAT_TYPE.getKey(this.getBoatType());

        if (location != null) {
            tag.putString("SketchBoatType", location.toString());
        }
    }

    default void readSaveData(CompoundTag tag) {
        ResourceLocation typeId = ResourceLocation.tryParse(tag.getString("SketchBoatType"));
        SketchBoatType type = SketchBuiltInRegistries.BOAT_TYPE.get(typeId);

        if (type != null) {
            this.setBoatType(type);
        }
    }

    default Component getDescription(EntityType<?> type) {
        return Component.translatable(Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(type)));
    }
}
