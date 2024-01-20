package com.teamabode.sketch.core.api.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationChannel.*;
import net.minecraft.client.animation.KeyframeAnimations;
import org.joml.Vector3f;

public class ParseUtils {

    public static AnimationChannel.Interpolation getInterpolation(InterpolationType type) {
        return switch (type) {
            case LINEAR -> Interpolations.LINEAR;
            case CATMULLROM -> Interpolations.CATMULLROM;
        };
    }

    public static Vector3f getTargetVec(Vector3f vector3f, TargetType target) {
        return switch (target) {
            case POSITION -> KeyframeAnimations.posVec(vector3f.x, vector3f.y, vector3f.z);
            case ROTATION -> KeyframeAnimations.degreeVec(vector3f.x, vector3f.y, vector3f.z);
            case SCALE -> KeyframeAnimations.scaleVec(vector3f.x, vector3f.y, vector3f.z);
        };
    }
}
