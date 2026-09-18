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

    public static BakedQuad baseFaceQuad(Direction side, TextureAtlasSprite sprite) {
        return layerFaceQuad(side, sprite, 0, 0);
    }

    public static BakedQuad layerFaceQuad(Direction side, TextureAtlasSprite sprite, int tintIndex, int layerNumber) {
        Vector3f from = new Vector3f(0f, 0f, 0f);
        Vector3f to = new Vector3f(16f, 16f, 16f);
        switch (side) {
            case DOWN -> from.y = 0.0f;
            case UP -> to.y = 16f;
            case NORTH -> from.z = 0.0f;
            case SOUTH -> to.z = 16f;
            case WEST -> from.x = 0.0f;
            case EAST -> to.x = 16f;
        }

        BlockFaceUV uv = new BlockFaceUV(new float[]{0f, 0f, 16f, 16f}, 0);
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
