package com.teamabode.scribe.core.api.pending;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamabode.scribe.Scribe;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class OldProperty<T> {
    String key;
    boolean loaded;
    T defaultValue;
    T loadedValue;
    Consumer<T> handler;
    Predicate<JsonObject> validater;
    Function<JsonObject, T> loader;
    Function<T, JsonElement> serializer;

    public OldProperty(String key, T defaultValue, Consumer<T> handler, Predicate<JsonObject> validater, Function<JsonObject, T> loader, Function<T, JsonElement> serializer){
        this.key = key;
        this.defaultValue = defaultValue;
        this.handler = handler;
        this.validater = validater;
        this.loader = loader;
        this.serializer = serializer;
    }

    public T get(){
        if(loaded) return loadedValue;

        return defaultValue;
    }

    public void load(JsonObject jsonObject) {
        if(!jsonObject.has(key)) {
            Scribe.LOGGER.info("Missing key " + key + " on " + jsonObject);

            return;
        }

        if(!validater.test(jsonObject)) throw new AssertionError("Invalid config property!");

        loadedValue = loader.apply(jsonObject);
        loaded = true;
    }

    public void handle(){
        handler.accept(get());
    }

    public JsonElement toJson(){
        return serializer.apply(get());
    }
}