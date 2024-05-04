package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntProperty extends ConfigProperty<Integer> {

    public IntProperty(String name, int defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Integer get() {
        return (int) this.value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((int) this.value);
    }

    @Override
    public Integer fromJson(JsonElement element) {
        return element.getAsInt();
    }
}
