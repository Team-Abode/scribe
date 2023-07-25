package com.teamabode.scribe.core.api.third_config_rewrite.property;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanProperty extends Property<Boolean> {

    public BooleanProperty(boolean value) {
        super(value);
    }

    public JsonElement toJson() {
        return new JsonPrimitive(this.getDefaultValue());
    }
}
