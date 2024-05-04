package com.teamabode.sketch.core.api.config;

import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Config {
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    protected final String name;
    protected final Map<String, List<ConfigProperty<?>>> categories;

    public Config(String name) {
        this.name = name;
        this.categories = Maps.newHashMap();
    }

    public void defineCategory(String name, ConfigProperty<?>... properties) {
        this.categories.put(name, List.of(properties));
    }

    public File getFile() {
        return CONFIG_DIR.resolve(this.name + ".json").toFile();
    }
}
