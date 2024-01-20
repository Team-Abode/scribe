package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class GroupBuilder {
    private final String name;
    private final JsonObject root = new JsonObject();
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<String, Object> defaults = new HashMap<>();

    protected GroupBuilder(String name) {
        this.name = name;
    }

    public GroupBuilder addProperty(String key, String defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addBooleanProperty(String key, boolean defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addIntProperty(String key, int defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addFloatProperty(String key, float defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public GroupBuilder addGroup(Group group) {
        groups.put(group.getName(), group);
        root.add(group.getName(), group.getGroupObject());
        return this;
    }

    public Group build() {
        Group group = new Group(name, root);
        group.setMaps(groups, defaults);
        return group;
    }
}
