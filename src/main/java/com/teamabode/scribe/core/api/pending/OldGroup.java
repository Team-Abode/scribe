package com.teamabode.scribe.core.api.pending;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class OldGroup {
    public HashMap<String, OldProperty<?>> properties = new HashMap<>();

    public OldGroup property(String key, String defaultValue, Consumer<String> handler) {
        properties.put(key, new OldProperty<>(key, defaultValue, handler, jsonObject -> GsonHelper.isStringValue(jsonObject, key), jsonObject -> GsonHelper.getAsString(jsonObject, key), value -> new JsonPrimitive(value)));

        return this;
    }

    public OldGroup property(String key, String defaultValue) {
        properties.put(key, new OldProperty<>(key, defaultValue, value -> {
        }, jsonObject -> GsonHelper.isStringValue(jsonObject, key), jsonObject -> GsonHelper.getAsString(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, int defaultValue, Consumer<Integer> handler) {
        properties.put(key, new OldProperty<>(key, defaultValue, handler, jsonObject -> GsonHelper.isNumberValue(jsonObject, key), jsonObject -> GsonHelper.getAsInt(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, int defaultValue) {
        properties.put(key, new OldProperty<>(key, defaultValue, value -> {
        }, jsonObject -> GsonHelper.isNumberValue(jsonObject, key), jsonObject -> GsonHelper.getAsInt(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, float defaultValue, Consumer<Float> handler) {
        properties.put(key, new OldProperty<>(key, defaultValue, handler, jsonObject -> GsonHelper.isNumberValue(jsonObject, key), jsonObject -> GsonHelper.getAsFloat(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, float defaultValue) {
        properties.put(key, new OldProperty<>(key, defaultValue, value -> {
        }, jsonObject -> GsonHelper.isNumberValue(jsonObject, key), jsonObject -> GsonHelper.getAsFloat(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, boolean defaultValue, Consumer<Boolean> handler) {
        properties.put(key, new OldProperty<>(key, defaultValue, handler, jsonObject -> GsonHelper.isBooleanValue(jsonObject, key), jsonObject -> GsonHelper.getAsBoolean(jsonObject, key), JsonPrimitive::new));

        return this;
    }

    public OldGroup property(String key, boolean defaultValue) {
        properties.put(key, new OldProperty<>(key, defaultValue, value -> {
        }, jsonObject -> GsonHelper.isBooleanValue(jsonObject, key), jsonObject -> GsonHelper.getAsBoolean(jsonObject, key), value -> new JsonPrimitive(value)));

        return this;
    }

    public OldGroup convertJsonGroup(JsonObject jsonObject) {
        OldGroup group = new OldGroup();

        jsonObject.entrySet().forEach(entry -> {
            String key = entry.getKey();
            JsonElement jsonElement = entry.getValue();

            if(jsonElement.isJsonObject()){
                group.group(key, convertJsonGroup(jsonElement.getAsJsonObject()));
            } else if(jsonElement.isJsonArray()){
                group.property(key, convertJsonArray(jsonElement.getAsJsonArray()));
            } else if(jsonElement.isJsonPrimitive()){
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

                if(jsonPrimitive.isString()){
                    group.property(key, jsonPrimitive.getAsString());
                } else if(jsonPrimitive.isNumber()){
                    group.property(key, jsonPrimitive.getAsFloat());
                } else if(jsonPrimitive.isBoolean()){
                    group.property(key, jsonPrimitive.getAsBoolean());
                }
            }
        });

        return group;
    }

    public List<Object> convertJsonArray(JsonArray jsonArray) {
        ArrayList<Object> list = new ArrayList<>();

        jsonArray.forEach(jsonElement -> {
            if(jsonElement.isJsonNull()) {
                list.add(null);
            } else if(jsonElement.isJsonArray()){
                list.add(convertJsonArray(jsonElement.getAsJsonArray()));
            } else if(jsonElement.isJsonObject()){
                list.add(convertJsonGroup(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonPrimitive()){
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

                if(jsonPrimitive.isString()){
                    list.add(jsonPrimitive.getAsString());
                } else if(jsonPrimitive.isNumber()){
                    list.add(jsonPrimitive.getAsFloat());
                } else if(jsonPrimitive.isBoolean()){
                    list.add(jsonPrimitive.getAsBoolean());
                }
            }
        });

        return  list;
    }

    public JsonArray convertToJsonArray(List<Object> list) {
        JsonArray jsonArray = new JsonArray();

        list.forEach(value -> {
            if(value instanceof OldGroup){
                jsonArray.add(((OldGroup)value).toJson());
            } else if(value instanceof List) {
                jsonArray.add(convertToJsonArray((List<Object>) value));
            } else if(value instanceof Integer) {
                jsonArray.add(new JsonPrimitive((int) value));
            } else if(value instanceof Float) {
                jsonArray.add(new JsonPrimitive((float) value));
            } else if(value instanceof String) {
                jsonArray.add(new JsonPrimitive((String) value));
            } else if(value instanceof Boolean) {
                jsonArray.add(new JsonPrimitive((boolean) value));
            }
        });

        return jsonArray;
    }

    public OldGroup property(String key, List<Object> defaultValue, Consumer<List<Object>> handler) {
        properties.put(key, new OldProperty<>(key, defaultValue, handler, jsonObject -> GsonHelper.isArrayNode(jsonObject, key), jsonObject -> convertJsonArray(GsonHelper.getAsJsonArray(jsonObject, key)), value -> ((OldGroup) value).toJson()));

        return this;
    }

    public OldGroup property(String key, List<Object> defaultValue) {
        properties.put(key, new OldProperty<>(key, defaultValue, value -> {
        }, jsonObject -> GsonHelper.isArrayNode(jsonObject, key), jsonObject -> convertJsonArray(GsonHelper.getAsJsonArray(jsonObject, key)), value -> ((OldGroup) value).toJson()));

        return this;
    }

    public OldGroup group(String key, Consumer<OldGroup> handler) {
        OldGroup group = new OldGroup();

        handler.accept(group);

        properties.put(
                key,
                new OldProperty<>(key, group, passedGroup -> group.handle(), jsonObject -> GsonHelper.isObjectNode(jsonObject, key), jsonObject -> group.load(jsonObject.getAsJsonObject(key)), OldGroup::toJson));

        return this;
    }

    public OldGroup group(String key, OldGroup group){
        properties.put(key, new OldProperty<>(key, group, passedGroup -> group.handle(), jsonObject -> GsonHelper.isObjectNode(jsonObject, key), jsonObject -> group.load(jsonObject.getAsJsonObject(key)), OldGroup::toJson));

        return this;
    }

    public OldGroup load(JsonObject jsonObject){
        properties.forEach((String key, OldProperty property) -> property.load(jsonObject));

        return this;
    }

    public void handle() {
        properties.forEach((String key, OldProperty property) -> property.handle());
    }

    public Object get(String key){
        if(!properties.containsKey(key)) return null;

        return properties.get(key).get();
    }

    public OldGroup getGroup(String key){
        if(!properties.containsKey(key)) return null;

        return (OldGroup) properties.get(key).get();
    }

    public JsonObject toJson(){
        JsonObject jsonObject = new JsonObject();

        properties.forEach((String key, OldProperty property) -> {
            jsonObject.add(key, property.toJson());
        });

        return jsonObject;
    }
}