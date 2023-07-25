package com.teamabode.scribe.core.api.third_config_rewrite;

import com.google.gson.JsonObject;
import com.teamabode.scribe.core.api.third_config_rewrite.property.Property;

public class ConfigBuilder {

    private final String name;
    private final JsonObject data;

    public ConfigBuilder(String name) {
        this.name = name;
        this.data = new JsonObject();
    }

    public <T> ConfigBuilder add(String name, Property<T> property) {
        data.add(name, property.toJson());
        return this;
    }
}
