package com.teamabode.sketch.core.mixin;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {



    @Redirect(method = "method_20723", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/Advancement$Builder;fromJson(Lcom/google/gson/JsonObject;Lnet/minecraft/advancements/critereon/DeserializationContext;)Lnet/minecraft/advancements/Advancement$Builder;"))
    private Advancement.Builder test(JsonObject json, DeserializationContext context) {
        Advancement.Builder builder = Advancement.Builder.fromJson(json, context);

        if (context.getAdvancementId().equals(new ResourceLocation("husbandry/plant_seed"))) {

        }

        return builder;
    }
}
