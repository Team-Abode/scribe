package com.teamabode.scribe;

import com.teamabode.scribe.core.api.animation.AnimationManager;
import com.teamabode.scribe.core.registry.ScribeFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scribe implements ModInitializer {
    public static final String MOD_ID = "scribe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final AnimationManager ANIMATION_MANAGER = new AnimationManager();

    public void onInitialize() {
        LOGGER.info("Initializing Scribe...");

        ScribeFeatures.register();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ANIMATION_MANAGER);
    }
}
