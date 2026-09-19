package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

public final class PatternBlockQuadBuilder {

    private static final FaceBakery FACE_BAKERY = new FaceBakery();

    private PatternBlockQuadBuilder() {}

    public static BakedQuad baseFaceQuad(Direction side, TextureAtlasSprite sprite, int tintIndex) {
        return layerFaceQuad(side, sprite, PatternBlockFaces.Orientation.R0_NONE, tintIndex);
    }

    public static BakedQuad layerFaceQuad(Direction side, TextureAtlasSprite sprite, PatternBlockFaces.Orientation orientation, int tintIndex) {
        Vector3f from = new Vector3f(0f, 0f, 0f);
        Vector3f to = new Vector3f(16f, 16f, 16f);

        switch (side) {
            case DOWN -> to.y = 0.0F;
            case UP -> from.y = 16.0F;
            case NORTH -> to.z = 0.0F;
            case SOUTH -> from.z = 16.0F;
            case WEST -> to.x = 0.0F;
            case EAST -> from.x = 16.0F;
        }

        float[] uvs = new float[]{0f, 0f, 16f, 16f};

        if (orientation.flipHorizontal) {
            uvs = new float[]{16f, 0f, 0f, 16f};
        }

        BlockFaceUV uv = new BlockFaceUV(uvs, orientation.rotation);
        BlockElementFace face = new BlockElementFace(null, tintIndex, sprite.contents().name().toString(), uv);

        return FACE_BAKERY.bakeQuad(
                from, to,
                face,
                sprite,
                side,
                BlockModelRotation.X0_Y0,
                null,
                true
        );
    }
}