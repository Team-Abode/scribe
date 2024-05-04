package com.teamabode.sketch.core.api.config;

import com.google.gson.*;
import com.teamabode.sketch.Sketch;

import java.io.FileReader;
import java.io.FileWriter;

public class ConfigManager {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final ConfigManager INSTANCE = new ConfigManager();

    private ConfigManager() {

    }

    public void register(Config config) {
        if (!config.getFile().exists()) {
            writeConfig(config);
        }
        readConfig(config);
    }

    private void writeConfig(Config config) {
        try (FileWriter writer = new FileWriter(config.getFile())) {
            JsonObject configJson = this.serialize(config);
            String data = GSON.toJson(configJson);
            writer.write(data);
        }
        catch (Exception e) {
            Sketch.LOGGER.error("Failed to write configuration file {}: {}", config.name, e);
        }
    }

    private void readConfig(Config config) {
        try (FileReader reader = new FileReader(config.getFile())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            config.categories.forEach((categoryName, properties) -> {
                JsonObject categoryJson = root.getAsJsonObject(categoryName);
                properties.forEach(property -> {
                    Object newValue = property.fromJson(categoryJson.get(property.getName()));
                    property.set(newValue);
                });
            });
        }
        catch (Exception e) {
            Sketch.LOGGER.error("Failed to read configuration file {}: {}", config.name, e);
        }
    }

    private JsonObject serialize(Config config) {
        JsonObject root = new JsonObject();
        config.categories.forEach((categoryName, properties) -> {
            JsonObject categoryJson = new JsonObject();
            properties.forEach(property -> {
                String name = property.getName();
                JsonElement element = property.toJson();
                categoryJson.add(name, element);
            });
            root.add(categoryName, categoryJson);
        });
        return root;
    }
}
