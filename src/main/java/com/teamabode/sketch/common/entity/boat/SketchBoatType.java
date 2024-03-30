package com.teamabode.sketch.common.entity.boat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import com.teamabode.sketch.core.registry.SketchItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record SketchBoatType(String assetId, Holder<Item> boatItem, Holder<Item> chestBoatItem) {
    public static final Codec<SketchBoatType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("asset_id").forGetter(SketchBoatType::assetId),
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("boat_item").forGetter(SketchBoatType::boatItem),
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("chest_boat_item").forGetter(SketchBoatType::chestBoatItem)
    ).apply(instance, SketchBoatType::new));

    public static final SketchBoatType FALLBACK = register("fallback", Holder.direct(SketchItems.FALLBACK_BOAT), Holder.direct(SketchItems.FALLBACK_CHEST_BOAT));

    public static void init() {}

    public SketchBoatType(String assetId, Holder<Item> boatItem, Holder<Item> chestBoatItem) {
        this.assetId = assetId;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
    }

    private static SketchBoatType register(String name, Holder<Item> boatItem, Holder<Item> chestBoatItem) {
        SketchBoatType type = new SketchBoatType(name, boatItem, chestBoatItem);
        return Registry.register(SketchBuiltInRegistries.BOAT_TYPE, new ResourceLocation(Sketch.MOD_ID, name), type);
    }
}
