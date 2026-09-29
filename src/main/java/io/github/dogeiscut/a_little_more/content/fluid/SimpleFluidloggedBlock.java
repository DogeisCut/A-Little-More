package io.github.dogeiscut.a_little_more.content.fluid;

import io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster.SeepCrystalClusterBlock;
import io.github.dogeiscut.a_little_more.datagen.ALMBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public interface SimpleFluidloggedBlock extends SimpleWaterloggedBlock {

    @Override
    default boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return Fluidlogged.fromFluidState(fluid.defaultFluidState()) != Fluidlogged.EMPTY;
    }

    @Override
    default boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state, @NotNull FluidState fluidState) {
        if (!state.getValue(ALMBlockStateProperties.FLUIDLOGGED).getFluidSource().is(fluidState.getType()) && Fluidlogged.fromFluidState(fluidState) != Fluidlogged.EMPTY) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(ALMBlockStateProperties.FLUIDLOGGED, Fluidlogged.fromFluidState(fluidState)), 3);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    default @NotNull ItemStack pickupBlock(@Nullable Player player, @NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(ALMBlockStateProperties.FLUIDLOGGED) != Fluidlogged.EMPTY) {
            Fluidlogged oldFluidlogged = state.getValue(ALMBlockStateProperties.FLUIDLOGGED);
            level.setBlock(pos, state.setValue(ALMBlockStateProperties.FLUIDLOGGED, Fluidlogged.EMPTY), 3);
            if (player != null) {
                player.playSound(oldFluidlogged.getFluidSource().getType().getPickupSound().orElse(SoundEvents.EMPTY));
            }
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }

            return oldFluidlogged.toBucketStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    // REALLY?
    // NO ARGUMENTS???
    @Override
    default @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }
}
