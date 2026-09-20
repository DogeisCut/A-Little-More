package io.github.dogeiscut.a_little_more.content.blocks.stamping_table;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Face;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Orientation;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public final class StampingTablePatternFaceRenderer {

    // agony part 2 electric boogaloo

    private static final ResourceLocation BASE = ALittleMore.id("block/pattern_block/base");
    private static final ResourceLocation BASE_EMPTY = ALittleMore.id("block/pattern_block/base_empty");

    private static final float[] CORNER_U = {0.0F, 0.0F, 1.0F, 1.0F};
    private static final float[] CORNER_V = {0.0F, 1.0F, 1.0F, 0.0F};

    private StampingTablePatternFaceRenderer() {
    }

    public record FaceFrame(float originX, float originY, float rightX, float rightY, float downX, float downY) {

        public static @NotNull FaceFrame square(float x, float y, float size) {
            return new FaceFrame(x, y, size, 0.0F, 0.0F, size);
        }

        public float x(float fx, float fy) {
            return originX + fx * rightX + fy * downX;
        }

        public float y(float fx, float fy) {
            return originY + fx * rightY + fy * downY;
        }
    }

    public enum CubeView {
        FRONT(new float[]{-1, 0, -1}, new float[]{-1, 2, 1}, Direction.UP, Direction.EAST, Direction.NORTH),
        BACK(new float[]{1, 0, 1}, new float[]{-1, 2, 1}, Direction.DOWN, Direction.WEST, Direction.SOUTH);

        public final Direction endFace;
        public final Direction leftFace;
        public final Direction rightFace;
        private final float[] screenRight;
        private final float[] screenUp;

        CubeView(float[] screenRight, float[] screenUp, Direction endFace, Direction leftFace, Direction rightFace) {
            this.screenRight = normalize(screenRight);
            this.screenUp = normalize(screenUp);
            this.endFace = endFace;
            this.leftFace = leftFace;
            this.rightFace = rightFace;
        }

        public @NotNull CubeView opposite() {
            return this == FRONT ? BACK : FRONT;
        }

        public boolean shows(@NotNull Direction direction) {
            return direction == endFace || direction == leftFace || direction == rightFace;
        }

        public float[] screenDirection(@NotNull Direction direction) {
            float[] world = {direction.getStepX(), direction.getStepY(), direction.getStepZ()};
            float x = dot(world, screenRight);
            float y = -dot(world, screenUp);
            float length = (float) Math.sqrt(x * x + y * y);
            return new float[]{x / length, y / length};
        }

        private float[] project(float[] point, float centerX, float centerY, float scale) {
            float[] relative = {point[0] - 0.5F, point[1] - 0.5F, point[2] - 0.5F};
            return new float[]{centerX + dot(relative, screenRight) * scale, centerY - dot(relative, screenUp) * scale};
        }
    }

    private static final Map<Direction, float[][]> FACE_BASIS = new EnumMap<>(Direction.class);

    static {
        FACE_BASIS.put(Direction.UP, basis(0, 1, 0, 1, 0, 0, 0, 0, 1));
        FACE_BASIS.put(Direction.DOWN, basis(0, 0, 1, 1, 0, 0, 0, 0, -1));
        FACE_BASIS.put(Direction.NORTH, basis(1, 1, 0, -1, 0, 0, 0, -1, 0));
        FACE_BASIS.put(Direction.SOUTH, basis(0, 1, 1, 1, 0, 0, 0, -1, 0));
        FACE_BASIS.put(Direction.WEST, basis(0, 1, 0, 0, 0, 1, 0, -1, 0));
        FACE_BASIS.put(Direction.EAST, basis(1, 1, 1, 0, 0, -1, 0, -1, 0));
    }

    public static void drawFace(@NotNull GuiGraphics graphics, @NotNull FaceFrame frame, @Nullable Face face, float shade) {
        Matrix4f matrix = graphics.pose().last().pose();
        BufferBuilder buffer = beginTextured();
        emitFace(buffer, matrix, frame, face, shade);
        endBatch(buffer);
    }

    public static void drawPatternIcon(@NotNull GuiGraphics graphics, @NotNull PatternBlockPattern pattern,
                                       int x, int y, int size, int baseRgb, int patternRgb) {
        Matrix4f matrix = graphics.pose().last().pose();
        FaceFrame frame = FaceFrame.square(x, y, size);
        Function<ResourceLocation, TextureAtlasSprite> atlas = atlas();

        BufferBuilder buffer = beginTextured();
        emitSprite(buffer, matrix, frame, atlas.apply(BASE), Orientation.R0_NONE, baseRgb, 1.0F);
        emitSprite(buffer, matrix, frame, atlas.apply(pattern.assetId()), Orientation.R0_NONE, patternRgb, 1.0F);
        endBatch(buffer);
    }

    public static void drawCube(@NotNull GuiGraphics graphics, @NotNull PatternBlockFaces faces, @NotNull CubeView view,
                                float centerX, float centerY, float scale, @Nullable Direction highlight) {
        Matrix4f matrix = graphics.pose().last().pose();
        FaceFrame end = cubeFace(view, view.endFace, centerX, centerY, scale);
        FaceFrame left = cubeFace(view, view.leftFace, centerX, centerY, scale);
        FaceFrame right = cubeFace(view, view.rightFace, centerX, centerY, scale);

        BufferBuilder textured = beginTextured();
        emitFace(textured, matrix, end, faces.getFace(view.endFace).orElse(null), 1.0F);
        emitFace(textured, matrix, left, faces.getFace(view.leftFace).orElse(null), 0.82F);
        emitFace(textured, matrix, right, faces.getFace(view.rightFace).orElse(null), 0.64F);
        endBatch(textured);

        BufferBuilder lines = beginColored();
        emitOutline(lines, matrix, end, 1.0F, 0xFF4A4A4A);
        emitOutline(lines, matrix, left, 1.0F, 0xFF4A4A4A);
        emitOutline(lines, matrix, right, 1.0F, 0xFF4A4A4A);
        if (highlight != null && view.shows(highlight)) {
            emitOutline(lines, matrix, cubeFace(view, highlight, centerX, centerY, scale), 2.0F, 0xFF000000);
        }
        endBatch(lines);
    }

    public static @NotNull FaceFrame cubeFace(@NotNull CubeView view, @NotNull Direction direction,
                                              float centerX, float centerY, float scale) {
        float[][] basis = FACE_BASIS.get(direction);
        float[] origin = view.project(basis[0], centerX, centerY, scale);
        float[] right = view.project(add(basis[0], basis[1]), centerX, centerY, scale);
        float[] down = view.project(add(basis[0], basis[2]), centerX, centerY, scale);
        return new FaceFrame(origin[0], origin[1], right[0] - origin[0], right[1] - origin[1],
                down[0] - origin[0], down[1] - origin[1]);
    }

    public static void drawLine(@NotNull GuiGraphics graphics, float x0, float y0, float x1, float y1, float thickness, int argb) {
        BufferBuilder buffer = beginColored();
        emitLine(buffer, graphics.pose().last().pose(), x0, y0, x1, y1, thickness, argb);
        endBatch(buffer);
    }

    private static void emitFace(@NotNull BufferBuilder buffer, @NotNull Matrix4f matrix, @NotNull FaceFrame frame,
                                 @Nullable Face face, float shade) {
        Function<ResourceLocation, TextureAtlasSprite> atlas = atlas();
        if (face == null) {
            emitSprite(buffer, matrix, frame, atlas.apply(BASE_EMPTY), Orientation.R0_NONE, 0xFFFFFF, shade);
            return;
        }

        emitSprite(buffer, matrix, frame, atlas.apply(BASE), Orientation.R0_NONE, rgb(face.baseColor().getTextureDiffuseColor()), shade);
        for (PatternBlockFaces.Layer layer : face.layers()) {
            emitSprite(buffer, matrix, frame, atlas.apply(layer.pattern().value().assetId()), face.orientation(),
                    rgb(layer.color().getTextureDiffuseColor()), shade);
        }
    }

    private static void emitSprite(@NotNull BufferBuilder buffer, @NotNull Matrix4f matrix, @NotNull FaceFrame frame,
                                   @NotNull TextureAtlasSprite sprite, @NotNull Orientation orientation, int rgb, float shade) {
        float red = ((rgb >> 16) & 0xFF) / 255.0F * shade;
        float green = ((rgb >> 8) & 0xFF) / 255.0F * shade;
        float blue = (rgb & 0xFF) / 255.0F * shade;

        float[] xs = new float[4];
        float[] ys = new float[4];
        for (int i = 0; i < 4; i++) {
            float fx = CORNER_U[i];
            float fy = CORNER_V[i];
            if (orientation.isFlip()) {
                fx = 1.0F - fx;
            }
            for (int turn = 0; turn < orientation.getRotation() / 90; turn++) {
                float rotatedX = 1.0F - fy;
                fy = fx;
                fx = rotatedX;
            }
            xs[i] = frame.x(fx, fy);
            ys[i] = frame.y(fx, fy);
        }

        for (int i : windingOrder(xs, ys)) {
            buffer.addVertex(matrix, xs[i], ys[i], 0.0F)
                    .setUv(lerp(sprite.getU0(), sprite.getU1(), CORNER_U[i]), lerp(sprite.getV0(), sprite.getV1(), CORNER_V[i]))
                    .setColor(red, green, blue, 1.0F);
        }
    }

    private static void emitOutline(@NotNull BufferBuilder buffer, @NotNull Matrix4f matrix, @NotNull FaceFrame frame,
                                    float thickness, int argb) {
        float x00 = frame.x(0, 0), y00 = frame.y(0, 0);
        float x10 = frame.x(1, 0), y10 = frame.y(1, 0);
        float x11 = frame.x(1, 1), y11 = frame.y(1, 1);
        float x01 = frame.x(0, 1), y01 = frame.y(0, 1);
        emitLine(buffer, matrix, x00, y00, x10, y10, thickness, argb);
        emitLine(buffer, matrix, x10, y10, x11, y11, thickness, argb);
        emitLine(buffer, matrix, x11, y11, x01, y01, thickness, argb);
        emitLine(buffer, matrix, x01, y01, x00, y00, thickness, argb);
    }

    private static void emitLine(@NotNull BufferBuilder buffer, @NotNull Matrix4f matrix,
                                 float x0, float y0, float x1, float y1, float thickness, int argb) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length < 1.0E-4F) {
            return;
        }
        float ux = dx / length;
        float uy = dy / length;
        float half = thickness / 2.0F;

        x0 -= ux * half;
        y0 -= uy * half;
        x1 += ux * half;
        y1 += uy * half;

        float nx = -uy * half;
        float ny = ux * half;
        float[] xs = {x0 + nx, x1 + nx, x1 - nx, x0 - nx};
        float[] ys = {y0 + ny, y1 + ny, y1 - ny, y0 - ny};

        float alpha = ((argb >>> 24) & 0xFF) / 255.0F;
        float red = ((argb >> 16) & 0xFF) / 255.0F;
        float green = ((argb >> 8) & 0xFF) / 255.0F;
        float blue = (argb & 0xFF) / 255.0F;
        for (int i : windingOrder(xs, ys)) {
            buffer.addVertex(matrix, xs[i], ys[i], 0.0F).setColor(red, green, blue, alpha);
        }
    }

    private static void emitMyWillToLive() {

    }

    private static int[] windingOrder(float[] xs, float[] ys) {
        float area = 0.0F;
        for (int i = 0; i < 4; i++) {
            int next = (i + 1) % 4;
            area += xs[i] * ys[next] - xs[next] * ys[i];
        }
        return area < 0.0F ? new int[]{0, 1, 2, 3} : new int[]{3, 2, 1, 0};
    }

    private static @NotNull BufferBuilder beginTextured() {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        return Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
    }

    private static @NotNull BufferBuilder beginColored() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        return Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
    }

    private static void endBatch(@NotNull BufferBuilder buffer) {
        MeshData mesh = buffer.build();
        if (mesh != null) {
            BufferUploader.drawWithShader(mesh);
        }
    }

    private static @NotNull Function<ResourceLocation, TextureAtlasSprite> atlas() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
    }

    private static int rgb(int color) {
        return color & 0xFFFFFF;
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    private static float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    private static float[] add(float[] a, float[] b) {
        return new float[]{a[0] + b[0], a[1] + b[1], a[2] + b[2]};
    }

    private static float[] normalize(float[] v) {
        float length = (float) Math.sqrt(dot(v, v));
        return new float[]{v[0] / length, v[1] / length, v[2] / length};
    }

    private static float[][] basis(float ox, float oy, float oz, float rx, float ry, float rz,
                                                       float dx, float dy, float dz) {
        return new float[][]{{ox, oy, oz}, {rx, ry, rz}, {dx, dy, dz}};
    }
}
