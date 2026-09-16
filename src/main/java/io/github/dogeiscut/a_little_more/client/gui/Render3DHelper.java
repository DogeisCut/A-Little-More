package io.github.dogeiscut.a_little_more.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class Render3DHelper {

    // agony

    private static final float FLUID_HEIGHT = 14.0f / 16.0f; // horrible alternate dimension where minecraft decides to change this
    // upon further investigations, still fluids are actually slightly taller than this. I hate this game.

    public static void render3DBlock(GuiGraphics guiGraphics, BlockState state, float x, float y, float scale) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        setup3DMatrix(poseStack, x, y, scale);
        Lighting.setupFor3DItems();

        Minecraft mc = Minecraft.getInstance();
        mc.getBlockRenderer().renderSingleBlock(
                state,
                poseStack,
                guiGraphics.bufferSource(),
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY
        );

        guiGraphics.flush();
        poseStack.popPose();
    }

    public static void render3DFluid(GuiGraphics guiGraphics, FluidStack fluidStack, float x, float y, float scale) {
        if (fluidStack.isEmpty()) return;

        // TODO: fix transparency

        IClientFluidTypeExtensions clientFluid = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTex = clientFluid.getStillTexture(fluidStack);
        ResourceLocation flowingTex = clientFluid.getFlowingTexture(fluidStack);
        int tintColor = clientFluid.getTintColor(fluidStack);

        Minecraft mc = Minecraft.getInstance();
        TextureAtlasSprite stillSprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTex);
        TextureAtlasSprite flowingSprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(flowingTex);

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        setup3DMatrix(poseStack, x, y, scale);
        Lighting.setupFor3DItems();

        VertexConsumer buffer = guiGraphics.bufferSource().getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float a = ((tintColor >> 24) & 0xFF) / 255.0f;
        float r = ((tintColor >> 16) & 0xFF) / 255.0f;
        float g = ((tintColor >> 8) & 0xFF) / 255.0f;
        float b = (tintColor & 0xFF) / 255.0f;
        if (a <= 0.0f) a = 1.0f;

        drawFluidCube(buffer, matrix, stillSprite, flowingSprite, r, g, b, a);

        guiGraphics.flush();
        poseStack.popPose();
    }

    private static void setup3DMatrix(PoseStack poseStack, float x, float y, float scale) {
        poseStack.translate(x, y, 100.0f);
        poseStack.scale(scale, -scale, scale);
        poseStack.mulPose(Axis.XP.rotationDegrees(15.5f));
        poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
    }

    private static void drawFluidCube(VertexConsumer builder, Matrix4f m, TextureAtlasSprite stillSprite, TextureAtlasSprite flowingSprite, float r, float g, float b, float a) {
        float h = FLUID_HEIGHT;

        quad(builder, m,
                0, h, 0, 0, h, 1, 1, h, 1, 1, h, 0,
                stillSprite.getU0(), stillSprite.getU1(), stillSprite.getV0(), stillSprite.getV1(),
                r, g, b, a, 0, 1, 0);

        quad(builder, m,
                0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1,
                stillSprite.getU0(), stillSprite.getU1(), stillSprite.getV0(), stillSprite.getV1(),
                r * 0.5f, g * 0.5f, b * 0.5f, a, 0, -1, 0);

        float uRange = flowingSprite.getU1() - flowingSprite.getU0();
        float vRange = flowingSprite.getV1() - flowingSprite.getV0();

        float scaleU = 16.0f / flowingSprite.contents().width();
        float scaleV = 16.0f / flowingSprite.contents().height();

        float sideMinU = flowingSprite.getU0();
        float sideMaxU = sideMinU + uRange * scaleU;

        float sideMinV = flowingSprite.getV0();
        float sideMaxV = sideMinV + vRange * scaleV * h;

        quad(builder, m,
                1, h, 0, 1, 0, 0, 0, 0, 0, 0, h, 0,
                sideMinU, sideMaxU, sideMinV, sideMaxV,
                r * 0.8f, g * 0.8f, b * 0.8f, a, 0, 0, -1);

        quad(builder, m,
                0, h, 1, 0, 0, 1, 1, 0, 1, 1, h, 1,
                sideMinU, sideMaxU, sideMinV, sideMaxV,
                r * 0.8f, g * 0.8f, b * 0.8f, a, 0, 0, 1);

        quad(builder, m,
                0, h, 0, 0, 0, 0, 0, 0, 1, 0, h, 1,
                sideMinU, sideMaxU, sideMinV, sideMaxV,
                r * 0.6f, g * 0.6f, b * 0.6f, a, -1, 0, 0);

        quad(builder, m,
                1, h, 1, 1, 0, 1, 1, 0, 0, 1, h, 0,
                sideMinU, sideMaxU, sideMinV, sideMaxV,
                r * 0.6f, g * 0.6f, b * 0.6f, a, 1, 0, 0);
    }

    public static void render3DItem(GuiGraphics guiGraphics, ItemStack stack, float x, float y, float scale, float yOffset, float xRot, float zRot) {
        if (stack.isEmpty()) return;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        setup3DMatrix(poseStack, x, y, scale);

        poseStack.translate(0.5f, yOffset, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(zRot));
        poseStack.scale(0.5f, 0.5f, 0.5f);

        Lighting.setupFor3DItems();

        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                guiGraphics.bufferSource(),
                mc.level,
                0
        );

        guiGraphics.flush();
        poseStack.popPose();
    }

    private static void quad(VertexConsumer builder, Matrix4f m,
                             float x0, float y0, float z0,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float x3, float y3, float z3,
                             float u0, float u1, float v0, float v1,
                             float r, float g, float b, float a,
                             float nx, float ny, float nz) {

        vertex(builder, m, x0, y0, z0, u0, v0, r, g, b, a, nx, ny, nz);
        vertex(builder, m, x1, y1, z1, u0, v1, r, g, b, a, nx, ny, nz);
        vertex(builder, m, x2, y2, z2, u1, v1, r, g, b, a, nx, ny, nz);
        vertex(builder, m, x3, y3, z3, u1, v0, r, g, b, a, nx, ny, nz);
    }

    private static void vertex(VertexConsumer builder, Matrix4f m, float x, float y, float z, float u, float v, float r, float g, float b, float a, float nx, float ny, float nz) {
        builder.addVertex(m, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(nx, ny, nz);
    }
}