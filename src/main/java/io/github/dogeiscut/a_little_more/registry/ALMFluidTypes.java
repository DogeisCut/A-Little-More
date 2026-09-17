package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepFluidType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ALMFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ALittleMore.MOD_ID);

    public static final Supplier<SeepFluidType> SEEP = fluidType("seep", SeepFluidType::new);

    public static <T extends FluidType> @NotNull Supplier<T> fluidType(@NotNull String name, @NotNull Supplier<T> factory) {
        return FLUID_TYPES.register(name, factory);
    }

    public static void register(@NotNull IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
    }
}