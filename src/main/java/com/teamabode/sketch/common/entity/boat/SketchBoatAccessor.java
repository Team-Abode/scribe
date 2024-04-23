package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.core.registry.SketchRegistries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.Optional;

public interface SketchBoatAccessor {

    void setBoatType(Holder<SketchBoatType> type);

    Holder<SketchBoatType> getBoatType();

    default void addSaveData(CompoundTag tag) {
        tag.putString("sketch_boat_type", this.getBoatType().unwrapKey().orElse(SketchBoatType.FALLBACK).location().toString());
    }

    default void readSaveData(CompoundTag tag, RegistryAccess registryAccess) {
        ResourceLocation id = ResourceLocation.tryParse(tag.getString("sketch_boat_type"));
        Optional<ResourceKey<SketchBoatType>> optionalKey = Optional.ofNullable(id).map(resourceLocation -> ResourceKey.create(SketchRegistries.BOAT_TYPE, resourceLocation));
        optionalKey.flatMap(key -> registryAccess.registryOrThrow(SketchRegistries.BOAT_TYPE).getHolder(key)).ifPresent(this::setBoatType);
    }

    default Optional<Item> getItemFromReference(RegistryAccess registryAccess, Holder<Item> itemHolder) {
        Registry<Item> registry = registryAccess.registryOrThrow(Registries.ITEM);
        return itemHolder.unwrapKey().map(registry::get);
    }

    default Component getDescription(EntityType<?> type) {
        return Component.translatable(Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(type)));
    }
}
