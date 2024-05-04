package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class FloatProperty extends ConfigProperty<Float> {

    public FloatProperty(String name, float defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Float get() {
        return (float) this.value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((float) this.value);
    }

    @Override
    public Float fromJson(JsonElement element) {
        return element.getAsFloat();
    }
}
