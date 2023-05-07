package com.teamabode.scribe.core.api.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.client.animation.AnimationChannel;

import java.util.Iterator;
import java.util.Map;

public class AnimationData {

    private final JsonElement data;

    public AnimationData(JsonElement data) {
        this.data = data;
    }

    public float getLength() {
        if (data.getAsJsonObject().has("length")) {
            return data.getAsJsonObject().get("length").getAsFloat();
        }
        throw new AssertionError("Animation is missing a specified length");
    }

    public boolean isLooping() {
        if (data.getAsJsonObject().has("looping")) {
            return data.getAsJsonObject().get("looping").getAsBoolean();
        }
        return false;
    }

    public Iterator<Map.Entry<String, JsonElement>> getBones() {
        if (data.getAsJsonObject().has("bones")) {
            return data.getAsJsonObject().get("bones").getAsJsonObject().entrySet().iterator();
        }
        throw new AssertionError("Bones not specified in animation");
    }

    public Iterator<Map.Entry<String, JsonElement>> getAnimationTargets(String bone) {
        if (data.getAsJsonObject().get("bones").getAsJsonObject().has(bone)) {
            return data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().entrySet().iterator();
        }
        throw new AssertionError("Invalid or missing animation target on bone \"" + bone + "\"");
    }

    public Iterator<Map.Entry<String, JsonElement>> getTimestamps(String bone, String animTarget) {
        if (data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().has(animTarget)) {
            return data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().entrySet().iterator();
        }
        throw new AssertionError();
    }

    public AnimationChannel.Interpolation getInterpolationType(String bone, String animTarget, String timestamp) {
        if (data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().get(timestamp).getAsJsonObject().has("interpolation")) {
            String interpolationType = data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().get(timestamp).getAsJsonObject().get("interpolation").getAsString();
            switch (interpolationType) {
                case "linear" -> {
                    return AnimationChannel.Interpolations.LINEAR;
                }
                case "catmullrom" -> {
                    return AnimationChannel.Interpolations.CATMULLROM;
                }
                default -> throw new AssertionError("Invalid interpolation type \"" + interpolationType + "\"");
            }
        }
        throw new AssertionError("No interpolation type specified on timestamp \"" + timestamp + "\" on \"" + animTarget + "\" for bone \"" + bone + "\"");
    }

    public float[] getAnimationValue(String bone, String animTarget, String timestamp) {
        if (data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().get(timestamp).getAsJsonObject().has("value")) {
            JsonArray animValue = data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().get(timestamp).getAsJsonObject().get("value").getAsJsonArray();
            return new float[]{animValue.get(0).getAsFloat(), animValue.get(1).getAsFloat(), animValue.get(2).getAsFloat()};
        }
        throw new AssertionError();
    }

    public int countKeyframes(String bone, String animTarget) {
        if (data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().has(animTarget)) {
            return data.getAsJsonObject().get("bones").getAsJsonObject().get(bone).getAsJsonObject().get(animTarget).getAsJsonObject().entrySet().size();
        }
        throw new AssertionError();
    }
}