package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.google.common.collect.Maps;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PatternBlock extends RotatedPillarBlock implements EntityBlock {
    private static final Map<DyeColor, Block> BY_COLOR;
    private final DyeColor color;

    public DyeColor getColor() {
        return this.color;
    }

    public PatternBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        BY_COLOR.put(color, this);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new PatternBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemStack stack;
        if (blockEntity instanceof PatternBlockEntity patternBlockEntity) {
            stack = patternBlockEntity.getItem();
        } else {
            stack = super.getCloneItemStack(level, pos, state);
        }

        return stack;
    }

    public static Block byColor(DyeColor color) {
        return BY_COLOR.getOrDefault(color, ALMBlocks.WHITE_PATTERN_BLOCK.get());
    }

    static {
        BY_COLOR = Maps.newHashMap();
    }
}
