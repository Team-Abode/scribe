package com.teamabode.scribe.core.api.third_config_rewrite.property;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntProperty extends Property<Integer> {

    public IntProperty(int value) {
        super(value);
    }

    public JsonElement toJson() {
        return new JsonPrimitive(this.getDefaultValue());
    }
}
