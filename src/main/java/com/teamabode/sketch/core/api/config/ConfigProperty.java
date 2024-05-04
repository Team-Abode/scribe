package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonElement;

public abstract class ConfigProperty<T> {
    private final String name;
    protected Object value;

    public ConfigProperty(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    public void set(Object value) {
        this.value = value;
    }

    public abstract Object get();

    public String getName() {
        return this.name;
    }

    public abstract JsonElement toJson();

    public abstract Object fromJson(JsonElement element);
}
