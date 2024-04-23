package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class SketchDataSerializers {
    public static final EntityDataSerializer<Holder<SketchBoatType>> BOAT_TYPE = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(SketchRegistries.BOAT_TYPE));

    public static void init() {
        EntityDataSerializers.registerSerializer(BOAT_TYPE);
    }
}
