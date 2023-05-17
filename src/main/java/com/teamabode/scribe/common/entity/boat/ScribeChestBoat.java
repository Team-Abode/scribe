package com.teamabode.scribe.common.entity.boat;

import com.teamabode.scribe.common.item.ScribeBoatItem;
import com.teamabode.scribe.core.registry.ScribeBuiltInRegistries;
import com.teamabode.scribe.core.registry.ScribeDataSerializers;
import com.teamabode.scribe.core.registry.ScribeEntities;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ScribeChestBoat extends ChestBoat implements ScribeBoatAccessor {
    private static final EntityDataAccessor<ScribeBoatType> BOAT_TYPE = SynchedEntityData.defineId(ScribeChestBoat.class, ScribeDataSerializers.BOAT_TYPE);

    public ScribeChestBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public ScribeChestBoat(Level level, double x, double y, double z) {
        this(ScribeEntities.SCRIBE_CHEST_BOAT, level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, ScribeBoatType.DEFAULT);
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putString("scribe:boat_type", ScribeBuiltInRegistries.BOAT_TYPE.getKey(this.getBoatType()).toString());
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        ScribeBoatType boatType = ScribeBuiltInRegistries.BOAT_TYPE.get(ResourceLocation.tryParse(compound.getString("scribe:boat_type")));
        if (boatType != null) {
            this.setBoatType(boatType);
        }
    }

    protected Component getTypeName() {
        return getDescription();
    }

    public Component getDescription() {
        return Component.translatable(Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CHEST_BOAT)));
    }

    @Override
    public Item getDropItem() {
        if (ScribeBoatItem.TYPE_TO_CHEST_BOAT_ITEM.containsKey(this.getBoatType())) {
            return ScribeBoatItem.TYPE_TO_CHEST_BOAT_ITEM.get(this.getBoatType());
        }
        return super.getDropItem();
    }

    @Override
    public ItemStack getPickResult() {
        if (ScribeBoatItem.TYPE_TO_CHEST_BOAT_ITEM.containsKey(this.getBoatType())) {
            return new ItemStack(ScribeBoatItem.TYPE_TO_CHEST_BOAT_ITEM.get(this.getBoatType()));
        }
        return super.getPickResult();
    }

    public void setBoatType(ScribeBoatType type) {
        this.entityData.set(BOAT_TYPE, type);
    }

    public ScribeBoatType getBoatType() {
        return this.entityData.get(BOAT_TYPE);
    }
}
