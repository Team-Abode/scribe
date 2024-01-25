package com.teamabode.sketch.core.api.advancement.modifier;

import com.google.gson.JsonElement;
import com.teamabode.sketch.core.mixin.access.AdvancementBuilderAccessor;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

public interface RequirementModifier {

    void modify(Advancement.Builder advancement);

    JsonElement serializeToJson();

    ResourceLocation getId();

    default void setRequirements(Advancement.Builder advancement, String[][] requirements) {
        ((AdvancementBuilderAccessor) advancement).setRequirements(requirements);
    }

    default String[][] getRequirements(Advancement.Builder advancement) {
        return ((AdvancementBuilderAccessor) advancement).getRequirements();
    }
}
