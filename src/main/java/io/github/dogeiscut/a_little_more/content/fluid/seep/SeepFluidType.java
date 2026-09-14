package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

public class SeepFluidType extends BaseFluidType {
    public static final ResourceLocation STILL = ALittleMore.id("block/seep_still");
    public static final ResourceLocation FLOW = ALittleMore.id("block/seep_flow");
    public static final ResourceLocation OVERLAY = ALittleMore.id("misc/in_seep");

    public SeepFluidType() {
        super(FluidType.Properties.create()
                        .pathType(PathType.LAVA)
                        .canExtinguish(true)
                        .canDrown(false)
                        .adjacentPathType(PathType.DANGER_OTHER)
                        .canConvertToSource(false)
                        .canHydrate(true)
                        .canPushEntity(true)
                        .density(15)
                        .fallDistanceModifier(0)
                        .lightLevel(2)
                        .motionScale(0.5)
                        .rarity(Rarity.UNCOMMON)
                        .supportsBoating(false)
                        .viscosity(5),
                STILL,
                FLOW,
                OVERLAY,
                0xFFFFFF,
                new Vector3f(0.866666667f, 0.690196078f, 1.0f),
                1.0f,
                6.0f
        );
    }
}
