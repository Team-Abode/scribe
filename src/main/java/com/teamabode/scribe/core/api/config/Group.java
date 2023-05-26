package com.teamabode.scribe.core.api.config;

import com.google.gson.JsonObject;

public class Group {
    private final JsonObject group;
    private final String name;

    protected Group(String name, JsonObject group) {
        this.name = name;
        this.group = group;
    }

    public String getStringProperty(String key) {
        return group.get(key).getAsString();
    }

    public int getIntProperty(String key) {
        return group.get(key).getAsInt();
    }

    public boolean getBooleanProperty(String key) {
        return group.get(key).getAsBoolean();
    }

    public Group getGroup(String name) {
        return new Group(name, group.getAsJsonObject(name));
    }

    protected String getName() {
        return this.name;
    }

    protected JsonObject getGroupObject() {
        return this.group;
    }
}
