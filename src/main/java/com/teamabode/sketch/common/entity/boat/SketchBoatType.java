package com.teamabode.sketch.common.entity.boat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.core.registry.SketchRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record SketchBoatType(ResourceLocation assetId, Holder<Item> boatItem, Holder<Item> chestBoatItem) {
    public static final Codec<SketchBoatType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(SketchBoatType::assetId),
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("boat_item").forGetter(SketchBoatType::boatItem),
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("chest_boat_item").forGetter(SketchBoatType::chestBoatItem)
    ).apply(instance, SketchBoatType::new));

    public static final ResourceKey<SketchBoatType> FALLBACK = ResourceKey.create(SketchRegistries.BOAT_TYPE, Sketch.id("fallback"));

    public static void init() {}

    public SketchBoatType(ResourceLocation assetId, Holder<Item> boatItem, Holder<Item> chestBoatItem) {
        this.assetId = assetId;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
    }

    public static void setTypeFromKey(RegistryAccess registryAccess, SketchBoatAccessor boat, ResourceKey<SketchBoatType> type) {
        registryAccess.registryOrThrow(SketchRegistries.BOAT_TYPE).getHolder(type).ifPresent(boat::setBoatType);
    }
}
