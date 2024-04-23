package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.core.registry.SketchDataSerializers;
import com.teamabode.sketch.core.registry.SketchEntities;
import com.teamabode.sketch.core.registry.SketchRegistries;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SketchChestBoat extends ChestBoat implements SketchBoatAccessor {
    private static final EntityDataAccessor<Holder<SketchBoatType>> BOAT_TYPE = SynchedEntityData.defineId(SketchChestBoat.class, SketchDataSerializers.BOAT_TYPE);

    public SketchChestBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public SketchChestBoat(Level level, double x, double y, double z) {
        this(SketchEntities.SKETCH_CHEST_BOAT, level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOAT_TYPE, this.registryAccess().registryOrThrow(SketchRegistries.BOAT_TYPE).getHolderOrThrow(SketchBoatType.FALLBACK));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addSaveData(compound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readSaveData(compound, this.registryAccess());
    }

    @Override
    protected Component getTypeName() {
        return this.getDescription(EntityType.CHEST_BOAT);
    }

    @Override
    public Item getDropItem() {
        SketchBoatType type = this.getBoatType().value();
        return this.getItemFromReference(this.registryAccess(), type.chestBoatItem()).orElseGet(super::getDropItem);
    }

    @Override
    public ItemStack getPickResult() {
        SketchBoatType type = this.getBoatType().value();
        return this.getItemFromReference(this.registryAccess(), type.chestBoatItem()).orElseGet(super::getDropItem).getDefaultInstance();
    }

    public void setBoatType(Holder<SketchBoatType> type) {
        this.entityData.set(BOAT_TYPE, type);
    }

    public Holder<SketchBoatType> getBoatType() {
        return this.entityData.get(BOAT_TYPE);
    }
}
