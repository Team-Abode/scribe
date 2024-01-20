package com.teamabode.sketch.core.registry;

import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class SketchDataSerializers {

    public static final EntityDataSerializer<SketchBoatType> BOAT_TYPE = EntityDataSerializer.simpleId(SketchBuiltInRegistries.BOAT_TYPE);

    public static void init() {
        EntityDataSerializers.registerSerializer(BOAT_TYPE);
    }
}
