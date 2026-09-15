package io.github.dogeiscut.a_little_more.content.consumables.enseepened_pearl;

import io.github.dogeiscut.a_little_more.registry.ALMEntities;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class EnseepenedPearlEntity extends ThrowableItemProjectile {
    public EnseepenedPearlEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public EnseepenedPearlEntity(Level level, LivingEntity shooter) {
        super(ALMEntities.ENSEEPENED_PEARL.get(), shooter, level);
    }

    protected @NotNull Item getDefaultItem() {
        return ALMItems.ENSEEPENED_PEARL.get();
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        for(int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * (double)2.0F, this.getZ(), this.random.nextGaussian(), 0.0F, this.random.nextGaussian());
        }

        if (this.level() instanceof ServerLevel serverlevel) {
            if (!this.isRemoved()) {
                Entity entity = this.getOwner();
                if (entity != null && isAllowedToTeleportOwner(entity, serverlevel)) {
                    if (entity.isPassenger()) {
                        entity.unRide();
                    }

                    if (entity instanceof ServerPlayer serverplayer) {
                        if (serverplayer.connection.isAcceptingMessages()) {
                            entity.changeDimension(new DimensionTransition(serverlevel, this.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING));
                            entity.resetFallDistance();
                            serverplayer.resetCurrentImpulseContext();
                            this.playSound(serverlevel, this.position());
                        }
                    } else {
                        entity.changeDimension(new DimensionTransition(serverlevel, this.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING));
                        entity.resetFallDistance();
                        this.playSound(serverlevel, this.position());
                    }

                    this.discard();
                    return;
                }

                this.discard();
            }
        }

    }

    private static boolean isAllowedToTeleportOwner(Entity entity, Level level) {
        if (entity.level().dimension() != level.dimension()) {
            return entity.canUsePortal(true);
        } else {
            boolean result;
            if (entity instanceof LivingEntity livingentity) {
                result = livingentity.isAlive() && !livingentity.isSleeping();
            } else {
                result = entity.isAlive();
            }

            return result;
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof ServerPlayer && !entity.isAlive() && this.level().getGameRules().getBoolean(GameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH)) {
            this.discard();
        } else {
            super.tick();
        }

    }

    private void playSound(Level level, Vec3 pos) {
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
    }

    public boolean canChangeDimensions(Level oldLevel, @NotNull Level newLevel) {
        boolean result;
        if (oldLevel.dimension() == Level.END) {
            Entity var4 = this.getOwner();
            if (var4 instanceof ServerPlayer serverplayer) {
                result = super.canChangeDimensions(oldLevel, newLevel) && serverplayer.seenCredits;
                return result;
            }
        }

        result = super.canChangeDimensions(oldLevel, newLevel);
        return result;
    }

    protected void onInsideBlock(@NotNull BlockState state) {
        super.onInsideBlock(state);
        if (state.is(Blocks.END_GATEWAY)) {
            Entity var3 = this.getOwner();
            if (var3 instanceof ServerPlayer serverplayer) {
                serverplayer.onInsideBlock(state);
            }
        }

    }
}
