package com.teamabode.scribe.core.api.third_config_rewrite;

import com.google.gson.JsonObject;
import com.teamabode.scribe.core.api.third_config_rewrite.property.Property;

import java.util.Map;

public class Config {

    private final String name;
    private final JsonObject data;

    Map<String, Property<?>> propertyMap;

    protected Config(String name, JsonObject data) {
        this.name = name;
        this.data = data;
    }

    public <T> T get(Property<T> property) {
        return property.getCurrentValue();
    }
}
