package com.teamabode.scribe.core.api.pending;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamabode.scribe.Scribe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.GsonHelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class OldConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    private final String fileName;
    private final OldGroup root;

    private OldConfig(String id){
        this.fileName = id;
        this.root = new OldGroup();
    }

    private File getFile() {
        return CONFIG_DIR.resolve(fileName + ".json").toFile();
    }

    public static OldConfig define(String id){
        return new OldConfig(id);
    }

    public OldConfig property(String key, String defaultValue, Consumer<String> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public OldConfig property(String key, String defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public OldConfig property(String key, int defaultValue, Consumer<Integer> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public OldConfig property(String key, int defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public OldConfig property(String key, float defaultValue, Consumer<Float> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public OldConfig property(String key, float defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public OldConfig property(String key, boolean defaultValue, Consumer<Boolean> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public OldConfig property(String key, boolean defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public OldConfig property(String key, List<Object> defaultValue, Consumer<List<Object>> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public OldConfig property(String key, List<Object> defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public OldConfig group(String key, Consumer<OldGroup> handler){
        root.group(key, handler);

        return this;
    }

    public OldConfig load() {
        try {
            FileReader fileReader = new FileReader(this.getFile());

            JsonObject jsonObject = GsonHelper.parse(fileReader);

            fileReader.close();

            System.out.println("Read config file at " + this.getFile().getPath());

            if(!jsonObject.isJsonObject()) throw new AssertionError("Config file must be a json object not an array!");

            root.load(jsonObject);
        } catch (Exception exception) {
            Scribe.LOGGER.warn("Error loading config file at " + this.getFile().getPath());
            Scribe.LOGGER.error(exception.getMessage());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                FileWriter fileWriter = new FileWriter(this.getFile());
                fileWriter.write(gson.toJson(root.toJson()));
                fileWriter.close();
            } catch (Exception exception2){
                Scribe.LOGGER.warn("Error writing default config at " + this.getFile());
                Scribe.LOGGER.error(exception2.getMessage());
                Scribe.LOGGER.error(String.valueOf(exception2.getStackTrace()[0].getLineNumber()));
            }
        }

        root.handle();

        return this;
    }

    public Object get(String key){
        return root.get(key);
    }

    public OldGroup getGroup(String key){
        return (OldGroup) root.get(key);
    }
}
