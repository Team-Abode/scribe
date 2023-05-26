package com.teamabode.scribe.core.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamabode.scribe.Scribe;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class Config {
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final String modId;
    private JsonObject root;

    protected Config(String modId, JsonObject root) {
        this.modId = modId;
        this.root = root;
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
            Scribe.LOGGER.error("Failed to write configuration file at " + this.getFile().getPath(), io);
        }
    }

    private void read() {
        try (FileReader reader = new FileReader(this.getFile())) {
            root = (JsonObject) JsonParser.parseReader(reader);
        }
        catch (Exception io) {
            Scribe.LOGGER.error("Failed to read configuration file at " + this.getFile(), io);
        }
    }

    public String getStringProperty(String key) {
        return root.get(key).getAsString();
    }

    public int getIntProperty(String key) {
        return root.get(key).getAsInt();
    }

    public boolean getBooleanProperty(String key) {
        return root.get(key).getAsBoolean();
    }

    public Group getGroup(String key) {
        return new Group(key, root.get(key).getAsJsonObject());
    }

    private File getFile() {
        return CONFIG_DIR.resolve(modId + ".json").toFile();
    }
}
