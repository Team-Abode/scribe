package com.teamabode.sketch.core.api.config;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConfigBuilder {
    private final String fileName;
    private final JsonObject root = new JsonObject();
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<String, Object> defaults = new HashMap<>();

    public ConfigBuilder(String fileName) {
        this.fileName = fileName;
    }

    public ConfigBuilder addProperty(String key, String defaultValue) {
        root.addProperty(key, defaultValue);
        defaults.put(key, defaultValue);
        return this;
    }

    public ConfigBuilder addBooleanProperty(String key, boolean defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addIntProperty(String key, int defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addFloatProperty(String key, float defaultValue) {
        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addGroup(String name, Function<GroupBuilder, GroupBuilder> builder) {
        Group group = builder.apply(new GroupBuilder(name)).build();

        root.add(group.getName(), group.getGroupObject());
        groups.put(group.getName(), group);
        return this;
    }

    public Config build() {
        Config config = new Config(fileName, root);
        config.setDefaults(groups, defaults);
        config.run();
        return config;
    }
}