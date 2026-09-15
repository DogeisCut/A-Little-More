package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

public class SeepFluidType extends BaseFluidType {
    public static final ResourceLocation STILL = ALittleMore.id("block/seep_still");
    public static final ResourceLocation FLOW = ALittleMore.id("block/seep_flow");
    public static final ResourceLocation OVERLAY = ALittleMore.id("misc/in_seep");
    private static final double SINK_ACCELERATION = 0.04D;
    private static final double MAX_SINK_SPEED = -0.5D;
    private static final double HORIZONTAL_DRAG = 0.95D;
    public SeepFluidType() {
        super(FluidType.Properties.create()
                        .descriptionId("block.a_little_more.seep")
                        .fallDistanceModifier(0f)
                        .canExtinguish(true)
                        .canDrown(false)
                        .lightLevel(5)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                        .pathType(PathType.LAVA)
                        .adjacentPathType(PathType.DANGER_OTHER)
                        .density(15)
                        .rarity(Rarity.UNCOMMON)
                        .motionScale(-0.01d)
                        .viscosity(5),
                STILL,
                FLOW,
                OVERLAY,
                0xFFFFFFFF,
                new Vector3f(0.866666667f, 0.690196078f, 1.0f),
                1.0f,
                3.0f
        );
    }

    @Override
    public void setItemMovement(ItemEntity entity) {
        Vec3 delta = entity.getDeltaMovement();
        double newY = Math.max(delta.y - SINK_ACCELERATION, MAX_SINK_SPEED);
        entity.setDeltaMovement(delta.x * HORIZONTAL_DRAG, newY, delta.z * HORIZONTAL_DRAG);
    }

    @Override
    public boolean canSwim(Entity entity) {
        if (entity instanceof LivingEntity living && living.hasEffect(MobEffects.LEVITATION)) {
            return false;
        }
        return super.canSwim(entity);
    }
}