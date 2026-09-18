package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PatternBlockColor implements BlockColor {
    @Override
    public int getColor(@NotNull BlockState state, BlockAndTintGetter level, BlockPos pos, int tintIndex) {
        if (level == null || pos == null) return 0xFFFFFF;

        PatternBlockEntity patternBlockEntity = (PatternBlockEntity) level.getBlockEntity(pos);
        if (patternBlockEntity == null) return 0xFFFFFF;

        BannerPatternLayers patterns = patternBlockEntity.getPatterns();

        if (tintIndex == 0) {
            return patternBlockEntity.getBaseColor().getTextureDiffuseColor();
        }

        int layerIndex = tintIndex - 1;
        var layers = patterns.layers();
        if (layerIndex < 0 || layerIndex >= layers.size()) return 0xFFFFFF;

        BannerPatternLayers.Layer layer = layers.get(layerIndex);
        return layer.color().getTextureDiffuseColor();
    }
}
