package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeepFlowingFluid extends BaseFlowingFluid {

    protected SeepFlowingFluid(@NotNull Properties properties) {
        super(properties);
    }

    @Nullable
    public ParticleOptions getDripParticle() {
        return ALMParticles.FALLING_SEEP.get();
    }

    @Override
    public boolean isSource(@NotNull FluidState fluidState) {
        return false;
    }

    @Override
    public int getAmount(@NotNull FluidState fluidState) {
        return 6;
    }

    @Override
    public void animateTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull FluidState state, @NotNull RandomSource random) {
        if (random.nextInt(8) == 0) {

            level.addParticle(
                    ALMParticles.SEEP_BUBBLE.get(),
                    pos.getX() + random.nextDouble(),
                    pos.getY() + (random.nextDouble() - (1.0 - state.getOwnHeight())),
                    pos.getZ() + random.nextDouble(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    public static class Flowing extends SeepFlowingFluid {
        public Flowing(@NotNull Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
        }

        @Override
        protected boolean isRandomlyTicking() {
            return true;
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return state.getValue(LEVEL);
        }


    }

    public static class Source extends SeepFlowingFluid {
        public Source(@NotNull Properties properties) {
            super(properties);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return true;
        }

        @Override
        protected boolean isRandomlyTicking() {
            return true;
        }

        @Override
        public void animateTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull FluidState state, @NotNull RandomSource random) {
            super.animateTick(level, pos, state, random);
            // TODO: custom sound
            // TODO: figure out why this isnt making a noise
//            if (!state.isSource() && !state.getValue(FALLING)) {
//                if (random.nextInt(64) == 0) {
//                    level.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F);
//                }
//            }
        }

    }
}
