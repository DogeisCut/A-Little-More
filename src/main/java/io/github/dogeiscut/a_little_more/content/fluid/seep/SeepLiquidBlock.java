package io.github.dogeiscut.a_little_more.content.fluid.seep;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SeepLiquidBlock extends LiquidBlock {

    private static final int LEVITATION_DURATION_TICKS = 20;
    private static final int LEVITATION_AMPLIFIER = 2;
    private static final double PUSH_FORCE = 0.02d;

    public SeepLiquidBlock(Supplier<? extends FlowingFluid> fluidSupplier, Properties properties) {
        super(fluidSupplier.get(), properties);
    }

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        super.entityInside(state, level, pos, entity);

        float fluidHeight = level.getFluidState(pos).getHeight(level, pos);
        double fluidSurfaceY = pos.getY() + fluidHeight;

        if (entity.getBoundingBox().minY < fluidSurfaceY) {
            if (entity instanceof LivingEntity living && living.isAlive()) {
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
                // This may seem weird but remember that the Immunity mob effect exists
                if (living.hasEffect(MobEffects.LEVITATION)) {
                    Vec3 delta = living.getDeltaMovement();
                    living.setDeltaMovement(delta.x, delta.y + PUSH_FORCE, delta.z);

                    living.hasImpulse = true;
                }
            }
        }
    }
}