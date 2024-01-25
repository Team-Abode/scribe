package com.teamabode.sketch.core.api.advancement.modifier;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamabode.sketch.Sketch;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class AddElementModifier implements RequirementModifier {
    public static final ResourceLocation ID = Sketch.id("add_element");

    private final int index;
    private final String[] elements;

    public AddElementModifier(int index, String[] elements) {
        this.index = index;
        this.elements = elements;
    }

    public void modify(Advancement.Builder advancement) {
        String[][] requirements = this.getRequirements(advancement);
        if (this.index >= requirements.length || this.index < 0) {
            throw new IndexOutOfBoundsException(this.index);
        }
        ArrayList<String> newRequirementList = Lists.newArrayList(requirements[this.index]);
        newRequirementList.addAll(Arrays.asList(elements));
        requirements[this.index] = newRequirementList.toArray(new String[0]);
        this.setRequirements(advancement, requirements);
    }

    @Override
    public JsonElement serializeToJson() {
        JsonObject root = new JsonObject();
        JsonArray array = new JsonArray();
        Arrays.stream(elements).forEach(array::add);
        root.addProperty("index", this.index);
        root.add("elements", array);
        return root;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}
