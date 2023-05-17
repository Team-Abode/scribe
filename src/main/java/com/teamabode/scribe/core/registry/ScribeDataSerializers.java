package com.teamabode.scribe.core.registry;

import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ScribeDataSerializers {

    public static final EntityDataSerializer<ScribeBoatType> BOAT_TYPE = EntityDataSerializer.simpleId(ScribeBuiltInRegistries.BOAT_TYPE);

    public static void init() {
        EntityDataSerializers.registerSerializer(BOAT_TYPE);
    }
}
