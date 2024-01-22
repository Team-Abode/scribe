package com.teamabode.sketch.core.mixin.access;

import net.minecraft.advancements.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Advancement.Builder.class)
public interface AdvancementBuilderAccessor {

    @Accessor
    String[][] getRequirements();
}
