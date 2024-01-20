package com.teamabode.sketch.core.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamabode.sketch.Sketch;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final String fileName;
    private JsonObject root;

    private Map<String, Group> groups = new HashMap<>();
    private Map<String, Object> defaults = new HashMap<>();

    protected Config(String fileName, JsonObject root) {
        this.fileName = fileName;
        this.root = root;
    }

    protected void setDefaults(Map<String, Group> groups, Map<String, Object> defaults) {
        this.groups = groups;
        this.defaults = defaults;
    }

    protected void run() {
        if (!this.getFile().exists()) {
            write();
            return;
        }
        read();
    }

    private void write() {
        try (FileWriter writer = new FileWriter(this.getFile())) {
            String data = GSON.toJson(root);
            writer.write(data);
        }
        catch (Exception io) {
            Sketch.LOGGER.error("Failed to write configuration file at " + this.getFile().getPath(), io);
        }
    }

    private void read() {
        try (FileReader reader = new FileReader(this.getFile())) {
            root = JsonParser.parseReader(reader).getAsJsonObject();
        }
        catch (Exception io) {
            Sketch.LOGGER.error("Failed to read configuration file at " + this.getFile(), io);
        }
    }

    public String getStringProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsString() : (String) defaults.get(key);
    }

    public int getIntProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsInt() : (int) defaults.get(key);
    }

    public float getFloatProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsFloat() : (float) defaults.get(key);
    }

    public boolean getBooleanProperty(String key) {
        var value = root.get(key);
        return value != null ? value.getAsBoolean() : (boolean) defaults.get(key);
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

    private File getFile() {
        return CONFIG_DIR.resolve(fileName + ".json").toFile();
    }
}
