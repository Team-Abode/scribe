package com.teamabode.sketch.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoat;
import com.teamabode.sketch.common.entity.boat.SketchBoatType;
import com.teamabode.sketch.common.entity.boat.SketchChestBoat;
import com.teamabode.sketch.core.registry.SketchBuiltInRegistries;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

import java.util.Map;

public class SketchBoatRenderer extends EntityRenderer<Boat> {
    public static final ModelLayerLocation BOAT = new ModelLayerLocation(new ResourceLocation(Sketch.MOD_ID, "boat"), "main");
    public static final ModelLayerLocation CHEST_BOAT = new ModelLayerLocation(new ResourceLocation(Sketch.MOD_ID, "chest_boat"), "main");

    private final Map<SketchBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public SketchBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context);

        this.boatResources = SketchBuiltInRegistries.BOAT_TYPE.stream().collect(ImmutableMap.toImmutableMap(
                type -> type,
                type -> Pair.of(getTextureLocation(type, chestBoat), createBoatModel(context, type, chestBoat))
        ));
    }

    public void render(Boat boat, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.translate(0.0F, 0.375F, 0.0F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)boat.getHurtTime() - partialTicks;
        float g = boat.getDamage() - partialTicks;
        if (g < 0.0F) {
            g = 0.0F;
        }

        if (f > 0.0F) {
            matrixStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * g / 10.0F * (float)boat.getHurtDir()));
        }

        float h = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(h, 0.0F)) {
            matrixStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.getBoatPair(boat);
        ResourceLocation resourceLocation = pair.getFirst();
        ListModel<Boat> listModel = pair.getSecond();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listModel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(listModel.renderType(resourceLocation));
        listModel.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexConsumer2 = buffer.getBuffer(RenderType.waterMask());
            if (listModel instanceof WaterPatchModel waterPatchModel) {
                waterPatchModel.waterPatch().render(matrixStack, vertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        matrixStack.popPose();
        super.render(boat, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, SketchBoatType type, boolean chestBoat) {
        ModelPart modelPart = context.bakeLayer(chestBoat ? CHEST_BOAT : BOAT);

        return chestBoat ? new ChestBoatModel(modelPart) : new BoatModel(modelPart);
    }

    private static ResourceLocation getTextureLocation(SketchBoatType type, boolean chestBoat) {
        ResourceLocation location = SketchBuiltInRegistries.BOAT_TYPE.getKey(type);

        String path = chestBoat ? "textures/entity/chest_boat/" + type.name() + ".png" : "textures/entity/boat/" + type.name() + ".png";
        return new ResourceLocation(location.getNamespace(), path);
    }

    public ResourceLocation getTextureLocation(Boat boat) {
        return this.getBoatPair(boat).getFirst();
    }

    private Pair<ResourceLocation, ListModel<Boat>> getBoatPair(Boat boat) {
        if (boat instanceof SketchBoat scribeBoat) {
            return boatResources.get(scribeBoat.getBoatType());
        }
        if (boat instanceof SketchChestBoat scribeBoat) {
            return boatResources.get(scribeBoat.getBoatType());
        }
        return boatResources.get(SketchBoatType.FALLBACK);
    }
}
