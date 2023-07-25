package com.teamabode.scribe.core.api.third_config_rewrite.property;

import com.google.gson.JsonElement;

public abstract class Property<T> {
    private final T defaultValue;
    private T currentValue;

    protected Property(T value) {
        this.defaultValue = value;
        this.currentValue = value;
    }

    public abstract JsonElement toJson();

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(T currentValue) {
        this.currentValue = currentValue;
    }
}
