package com.teamabode.scribe.core.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamabode.scribe.Scribe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.GsonHelper;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Config {
    private final Path path;
    private final String id;
    private final Group root;

    Config(String id){
        this.id = id;
        this.path = getPath();
        this.root = new Group();
    }

    public static Config define(String id){
        return new Config(id);
    }

    public static Path getPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public Config property(String key, String defaultValue, Consumer<String> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public Config property(String key, String defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public Config property(String key, int defaultValue, Consumer<Integer> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public Config property(String key, int defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public Config property(String key, float defaultValue, Consumer<Float> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public Config property(String key, float defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public Config property(String key, boolean defaultValue, Consumer<Boolean> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public Config property(String key, boolean defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public Config property(String key, List<Object> defaultValue, Consumer<List<Object>> handler){
        root.property(key, defaultValue, handler);

        return this;
    }

    public Config property(String key, List<Object> defaultValue){
        root.property(key, defaultValue);

        return this;
    }

    public Config group(String key, Consumer<Group> handler){
        root.group(key, handler);

        return this;
    }

    public Config load(){
        try {
            FileReader fileReader = new FileReader(path.resolve(id + ".json").toString());

            JsonObject jsonObject = GsonHelper.parse(fileReader);

            fileReader.close();

            System.out.println("Read config file at " + path.resolve(id + ".json"));

            if(!jsonObject.isJsonObject()) throw new AssertionError("Config file must be a json object not an array!");

            root.load(jsonObject);
        } catch (Exception exception) {
            Scribe.LOGGER.warn("Error loading config file at " + path.resolve(id + ".json"));
            Scribe.LOGGER.error(exception.getMessage());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try{
                FileWriter fileWriter = new FileWriter(path.resolve(id + ".json").toString());
                fileWriter.write(gson.toJson(root.toJson()));
                fileWriter.close();
            }catch (Exception exception2){
                Scribe.LOGGER.warn("Error writing default config at " + path.resolve(id + ".json"));
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

    public Group getGroup(String key){
        return (Group) root.get(key);
    }
}
