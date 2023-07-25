package com.teamabode.scribe.core.api.third_config_rewrite.property;

import com.google.common.collect.BiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ObjectProperty extends Property<BiMap<String, ? extends Property<?>>> {

    protected ObjectProperty(BiMap<String, Property<?>> value) {
        super(value);
    }

    public <T extends Property<?>> T get(Property<?> property) {
        var map = this.getCurrentValue();
        var inverseMap = this.getCurrentValue().inverse();

        if (map.containsValue(property)) {
            return (T) map.get(inverseMap.get(property)).getCurrentValue();
        }
        throw new AssertionError();
    }

    public JsonElement toJson() {
        JsonObject data = new JsonObject();
        this.getCurrentValue().forEach((name, property) -> {
            data.add(name, property.toJson());
        });
        return data;
    }
}
