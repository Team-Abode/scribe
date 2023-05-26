package com.teamabode.scribe.core.api.config;


import com.google.gson.JsonObject;

public class ConfigBuilder {
    private final String fileName;
    private final JsonObject root = new JsonObject();

    public ConfigBuilder(String fileName) {
        this.fileName = fileName;
    }

    public ConfigBuilder addProperty(String key, String defaultValue) {
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addBooleanProperty(String key, boolean defaultValue) {
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addIntProperty(String key, int defaultValue) {
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addGroup(Group group) {
        root.add(group.getName(), group.getGroupObject());
        return this;
    }

    public Config build() {
        Config config = new Config(fileName, root);
        config.run();
        return config;
    }
}