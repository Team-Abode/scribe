package com.teamabode.scribe.core.api.config;

import com.google.gson.JsonObject;

public class GroupBuilder {
    private final JsonObject group = new JsonObject();
    private final String name;

    public GroupBuilder(String name) {
        this.name = name;
    }

    public GroupBuilder addProperty(String key, String defaultValue) {
        group.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addBooleanProperty(String key, boolean defaultValue) {
        group.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addIntProperty(String key, int defaultValue) {
        group.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addGroup(String key, Group group) {
        this.group.add(key, group.getGroupObject());
        return this;
    }

    public Group build() {
        return new Group(name, group);
    }
}
