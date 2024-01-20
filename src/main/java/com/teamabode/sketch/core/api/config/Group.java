package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Group {
    private final String name;
    private final JsonObject root;
    private Map<String, Group> groups = new HashMap<>();
    private Map<String, Object> defaults = new HashMap<>();

    protected Group(String name, JsonObject root) {
        this.name = name;
        this.root = root;
    }

    protected void setMaps(Map<String, Group> groups, Map<String, Object> defaults) {
        this.groups = groups;
        this.defaults = defaults;
    }

    protected Map<String, Group> getGroups() {
        return this.groups;
    }

    protected Map<String, Object> getDefaults() {
        return this.defaults;
    }

    public String getStringProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsString() : (String) defaults.get(key);
    }

    public int getIntProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsInt() : (Integer) defaults.get(key);
    }

    public float getFloatProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsFloat() : (Float) defaults.get(key);
    }

    public boolean getBooleanProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsBoolean() : (Boolean) defaults.get(key);
    }

    public Group getGroup(String key) {
        var value = root.get(key);
        if (value != null) {
            var group = new Group(key, root.get(key).getAsJsonObject());
            group.setMaps(groups.get(key).getGroups(), groups.get(key).getDefaults());
            return group;
        }
        return groups.get(key);
    }

    protected String getName() {
        return this.name;
    }

    protected JsonObject getGroupObject() {
        return this.root;
    }
}
