package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.common.item.SketchBoatItem;
import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import com.teamabode.sketch.core.registry.SketchDataSerializers;
import com.teamabode.sketch.core.registry.SketchEntities;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SketchBoat extends Boat implements SketchBoatAccessor {
    private static final EntityDataAccessor<SketchBoatType> BOAT_TYPE = SynchedEntityData.defineId(SketchBoat.class, SketchDataSerializers.BOAT_TYPE);

    public SketchBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public SketchBoat(Level level, double x, double y, double z) {
        this(SketchEntities.SKETCH_BOAT, level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getDisplayName();
        this.entityData.define(BOAT_TYPE, SketchBoatType.DEFAULT);
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putString("sketch:boat_type", SketchBuiltInRegistries.BOAT_TYPE.getKey(this.getBoatType()).toString());
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        SketchBoatType boatType = SketchBuiltInRegistries.BOAT_TYPE.get(ResourceLocation.tryParse(compound.getString("sketch:boat_type")));
        if (boatType != null) {
            this.setBoatType(boatType);
        }
    }

    protected Component getTypeName() {
        return getDescription();
    }

    public Component getDescription() {
        return Component.translatable(Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BOAT)));
    }

    @Override
    public Item getDropItem() {
        if (SketchBoatItem.TYPE_TO_BOAT_ITEM.containsKey(this.getBoatType())) {
            return SketchBoatItem.TYPE_TO_BOAT_ITEM.get(this.getBoatType());
        }
        return super.getDropItem();
    }

    @Override
    public ItemStack getPickResult() {
        if (SketchBoatItem.TYPE_TO_BOAT_ITEM.containsKey(this.getBoatType())) {
            return new ItemStack(SketchBoatItem.TYPE_TO_BOAT_ITEM.get(this.getBoatType()));
        }
        return super.getPickResult();
    }

    public void setBoatType(SketchBoatType type) {
        this.entityData.set(BOAT_TYPE, type);
    }

    public SketchBoatType getBoatType() {
        return this.entityData.get(BOAT_TYPE);
    }
}
