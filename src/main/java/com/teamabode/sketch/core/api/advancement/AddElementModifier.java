package com.teamabode.sketch.core.api.advancement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;

public class AddElementModifier implements RequirementModifier {
    private final int index;
    private final String[] elements;

    public AddElementModifier(int index, String[] elements) {
        this.index = index;
        this.elements = elements;
    }

    @Override
    public void toJSON(JsonObject root) {
        JsonArray array = new JsonArray();
        Arrays.stream(elements).forEach(array::add);
        root.addProperty("index", this.index);
        root.add("elements", array);
    }
}
