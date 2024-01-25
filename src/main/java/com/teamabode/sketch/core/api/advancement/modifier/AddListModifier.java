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

public class AddListModifier implements RequirementModifier {
    public static final ResourceLocation ID = Sketch.id("add_list");

    private final String[][] lists;

    public AddListModifier(String[][] lists) {
        this.lists = lists;
    }

    @Override
    public void modify(Advancement.Builder advancement) {
        String[][] requirements = this.getRequirements(advancement);
        ArrayList<String[]> newRequirements = Lists.newArrayList(requirements);
        newRequirements.addAll(Arrays.asList(lists));
        this.setRequirements(advancement, newRequirements.toArray(new String[0][]));
    }

    @Override
    public JsonElement serializeToJson() {
        JsonObject root = new JsonObject();
        JsonArray lists = new JsonArray();
        Arrays.stream(this.lists).forEach(strings -> {
            JsonArray list = new JsonArray();
            Arrays.stream(strings).forEach(list::add);
            lists.add(list);
        });
        return root;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}
