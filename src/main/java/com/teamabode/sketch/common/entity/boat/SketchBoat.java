package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.common.item.SketchBoatItem;
import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import com.teamabode.sketch.core.registry.SketchDataSerializers;
import com.teamabode.sketch.core.registry.SketchEntities;
import com.teamabode.sketch.core.registry.SketchRegistries;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getDisplayName();
        this.entityData.define(BOAT_TYPE, SketchBoatType.FALLBACK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addSaveData(compound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readSaveData(compound);
    }

    @Override
    protected Component getTypeName() {
        return this.getDescription();
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
            return SketchBoatItem.TYPE_TO_BOAT_ITEM.get(this.getBoatType()).getDefaultInstance();
        }
        return super.getPickResult();
    }

    public Component getDescription() {
        return Component.translatable(Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BOAT)));
    }

    public void setBoatType(SketchBoatType type) {
        this.entityData.set(BOAT_TYPE, type);
    }

    public SketchBoatType getBoatType() {
        return this.entityData.get(BOAT_TYPE);
    }
}
