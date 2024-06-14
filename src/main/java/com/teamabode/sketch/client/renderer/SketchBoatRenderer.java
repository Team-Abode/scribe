package com.teamabode.sketch.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamabode.sketch.Sketch;
import com.teamabode.sketch.common.entity.boat.SketchBoatAccessor;
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

public class SketchBoatRenderer extends EntityRenderer<Boat> {
    public static final ModelLayerLocation BOAT = new ModelLayerLocation(Sketch.id("boat"), "main");
    public static final ModelLayerLocation CHEST_BOAT = new ModelLayerLocation(Sketch.id("chest_boat"), "main");

    private final ListModel<Boat> model;
    private final boolean chestBoat;

    public SketchBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context);
        this.model = this.createBoatModel(context, chestBoat);
        this.chestBoat = chestBoat;
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
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        model.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer boatVertex = buffer.getBuffer(this.model.renderType(this.getTextureLocation(boat)));
        model.renderToBuffer(matrixStack, boatVertex, packedLight, OverlayTexture.NO_OVERLAY);
        if (!boat.isUnderWater()) {
            VertexConsumer waterPatchVertex = buffer.getBuffer(RenderType.waterMask());
            if (model instanceof WaterPatchModel waterPatchModel) {
                waterPatchModel.waterPatch().render(matrixStack, waterPatchVertex, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
        matrixStack.popPose();
        super.render(boat, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, boolean chestBoat) {
        ModelPart modelPart = context.bakeLayer(chestBoat ? CHEST_BOAT : BOAT);
        return chestBoat ? new ChestBoatModel(modelPart) : new BoatModel(modelPart);
    }

    @Override
    public ResourceLocation getTextureLocation(Boat boat) {
        ResourceLocation asset = ((SketchBoatAccessor) boat).getBoatType().value().assetId();
        String subdirectory = chestBoat ? "chest_boat" : "boat";

        return asset.withPath(path -> "textures/entity/" + subdirectory + "/" + path + ".png");
    }
}
