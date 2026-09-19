package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PatternBlockColor implements BlockColor {
    @Override
    public int getColor(@NotNull BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (level == null || pos == null || tintIndex < 0) return 0xFFFFFF;

        if (!(level.getBlockEntity(pos) instanceof PatternBlockEntity patternBlockEntity)) {
            return 0xFFFFFF;
        }

        PatternBlockFaces faces = patternBlockEntity.getFaces();

        int directionOrdinal = tintIndex / 100;
        int subIndex = tintIndex % 100;

        if (directionOrdinal >= Direction.values().length) {
            return 0xFFFFFF;
        }

        Direction side = Direction.values()[directionOrdinal];
        Optional<PatternBlockFaces.Face> faceOpt = faces.getFace(side);

        if (faceOpt.isEmpty()) {
            return 0xFFFFFF;
        }

        PatternBlockFaces.Face face = faceOpt.get();

        // subIndex == 0 represents face base color tint
        if (subIndex == 0) {
            return face.baseColor().getTextureDiffuseColor();
        }

        int layerIndex = subIndex - 1;
        var layers = face.layers();
        if (layerIndex < layers.size()) {
            return layers.get(layerIndex).color().getTextureDiffuseColor();
        }

        return 0xFFFFFF;
    }
}