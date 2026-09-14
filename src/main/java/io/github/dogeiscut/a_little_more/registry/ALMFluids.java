package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepFluidType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ALMFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ALittleMore.MOD_ID);

    public static final Supplier<Fluid> SEEP = FLUIDS.register("seep",
            () -> new BaseFlowingFluid.Source());
    public static final Supplier<Fluid> FLOWING_SEEP = FLUIDS.register("flowing_seep",
            () -> new BaseFlowingFluid.Flowing());

    public static void register(IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }
}
