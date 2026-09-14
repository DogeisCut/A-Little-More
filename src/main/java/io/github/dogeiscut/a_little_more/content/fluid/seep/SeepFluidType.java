package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

public class SeepFluidType extends BaseFluidType {
    public static final ResourceLocation STILL = ALittleMore.id("block/seep_still");
    public static final ResourceLocation FLOW = ALittleMore.id("block/seep_flow");
    public static final ResourceLocation OVERLAY = ALittleMore.id("misc/in_seep");

    public SeepFluidType() {
        super(FluidType.Properties.create()
                        .descriptionId("block.a_little_more.seep")
                        .fallDistanceModifier(0f)
                        .canExtinguish(true)
                        .canDrown(false)
                        .lightLevel(2)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                        .pathType(PathType.LAVA)
                        .adjacentPathType(PathType.DANGER_OTHER)
                        .density(15)
                        .rarity(Rarity.UNCOMMON)
                        .viscosity(5),
                STILL,
                FLOW,
                OVERLAY,
                0xFFFFFF,
                new Vector3f(0.866666667f, 0.690196078f, 1.0f),
                0.0f,
                2.0f
        );
    }
}
