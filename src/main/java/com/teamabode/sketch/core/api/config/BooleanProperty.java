package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanProperty extends ConfigProperty<Boolean> {

    public BooleanProperty(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Boolean get() {
        return (boolean) this.value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((boolean) this.value);
    }

    @Override
    public Boolean fromJson(JsonElement element) {
        return element.getAsBoolean();
    }
}
