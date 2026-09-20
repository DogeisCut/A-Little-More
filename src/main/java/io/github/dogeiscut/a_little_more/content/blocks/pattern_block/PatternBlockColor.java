package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PatternBlockColor implements BlockColor, ItemColor {
    private static int tint(@NotNull PatternBlockFaces faces, int tintIndex) {
        boolean flipped = tintIndex >= PatternBlockFlip.TINT_OFFSET;
        if (flipped) {
            tintIndex -= PatternBlockFlip.TINT_OFFSET;
        }

        int directionOrdinal = tintIndex / 100;
        int subIndex = tintIndex % 100;

        if (directionOrdinal >= Direction.values().length) {
            return 0xFFFFFF;
        }

        Direction side = Direction.values()[directionOrdinal];
        if (flipped) {
            side = PatternBlockFlip.source(side);
        }
        Optional<PatternBlockFaces.Face> faceOpt = faces.getFace(side);

        if (faceOpt.isEmpty()) {
            return 0xFFFFFF;
        }

        PatternBlockFaces.Face face = faceOpt.get();

        if (subIndex == 0) {
            return face.baseColor().getTextureDiffuseColor() & 0xFFFFFF;
        }

        int layerIndex = subIndex - 1;
        var layers = face.layers();
        if (layerIndex < layers.size()) {
            return layers.get(layerIndex).color().getTextureDiffuseColor() & 0xFFFFFF;
        }

        return 0xFFFFFF;
    }

    @Override
    public int getColor(@NotNull BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (level == null || pos == null || tintIndex < 0) return 0xFFFFFF;

        if (!(level.getBlockEntity(pos) instanceof PatternBlockEntity patternBlockEntity)) {
            return 0xFFFFFF;
        }

        return tint(patternBlockEntity.getFaces(), tintIndex);
    }

    @Override
    public int getColor(@NotNull ItemStack stack, int tintIndex) {
        if (tintIndex < 0) return 0xFFFFFFFF;

        PatternBlockFaces faces = stack.getOrDefault(ALMDataComponents.PATTERN_BLOCK_FACES, PatternBlockFaces.EMPTY);
        return 0xFF000000 | tint(faces, tintIndex);
    }
}
