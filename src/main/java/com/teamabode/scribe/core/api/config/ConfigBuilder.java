package com.teamabode.scribe.core.api.config;


import com.google.gson.JsonObject;
import com.teamabode.scribe.Scribe;

import java.util.HashMap;
import java.util.Map;

public class ConfigBuilder {
    private final String fileName;
    private final JsonObject root = new JsonObject();
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<String, Object> defaults = new HashMap<>();

    public ConfigBuilder(String fileName) {
        this.fileName = fileName;
    }

    public ConfigBuilder addProperty(String key, String defaultValue) {
        Scribe.LOGGER.info("Appending " + key);

        root.addProperty(key, defaultValue);
        defaults.put(key, defaultValue);
        return this;
    }

    public ConfigBuilder addBooleanProperty(String key, boolean defaultValue) {
        Scribe.LOGGER.info("Appending " + key);

        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addIntProperty(String key, int defaultValue) {
        Scribe.LOGGER.info("Appending " + key);

        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addFloatProperty(String key, float defaultValue) {
        Scribe.LOGGER.info("Appending " + key);

        defaults.put(key, defaultValue);
        root.addProperty(key, defaultValue);
        return this;
    }

    public ConfigBuilder addGroup(Group group) {
        Scribe.LOGGER.info("Appending group " + group.getName());

        root.add(group.getName(), group.getGroupObject());
        groups.put(group.getName(), group);
        return this;
    }

    public Config build() {
        Scribe.LOGGER.info("Building config!");
        Scribe.LOGGER.info(defaults.toString());

        Config config = new Config(fileName, root);
        config.setMaps(groups, defaults);
        config.run();
        return config;
    }
}