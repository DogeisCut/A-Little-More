package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SeepLiquidBlock extends LiquidBlock {

    private static final int LEVITATION_DURATION_TICKS = 20;
    private static final int LEVITATION_AMPLIFIER = 2;
    private static final double PUSH_FORCE = 0.02d;

    public SeepLiquidBlock(@NotNull Supplier<? extends FlowingFluid> fluidSupplier, @NotNull Properties properties) {
        super(fluidSupplier.get(), properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) == 0) {
            level.addParticle(
                    ALMParticles.SEEP_BUBBLE.get(),
                    pos.getX() + random.nextDouble(),
                    pos.getY() + random.nextDouble(),
                    pos.getZ() + random.nextDouble(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        super.entityInside(state, level, pos, entity);

        float fluidHeight = level.getFluidState(pos).getHeight(level, pos);
        double fluidSurfaceY = pos.getY() + fluidHeight;

        if (entity.getBoundingBox().minY < fluidSurfaceY) {
            if (entity.isAlive()) {
                if (entity instanceof LivingEntity living) {
                    if (!level.isClientSide) {
                        living.addEffect(new MobEffectInstance(
                                MobEffects.LEVITATION,
                                LEVITATION_DURATION_TICKS,
                                LEVITATION_AMPLIFIER,
                                false,
                                true,
                                true
                        ));
                    }
                    if (living.hasEffect(MobEffects.LEVITATION)) {
                        applyPushImpulseToEntity(living);
                    }
                } else {
                    applyPushImpulseToEntity(entity);
                }
            }
        }
    }

    // This fights with the item entity sinking behavior
    // It's an easy fix but I'm leaving it since ingame, the sinking usually wins
    // And it makes Seep act more mystical.
    private void applyPushImpulseToEntity(@NotNull Entity entity) {
        Vec3 delta = entity.getDeltaMovement();
        entity.setDeltaMovement(delta.x, delta.y + PUSH_FORCE, delta.z);

        entity.hasImpulse = true;
    }
}