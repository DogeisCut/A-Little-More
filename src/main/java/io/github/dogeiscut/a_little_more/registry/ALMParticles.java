package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ALMParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, ALittleMore.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_BUBBLE =
            PARTICLES.register("seep_bubble", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_BUBBLE_POP =
            PARTICLES.register("seep_bubble_pop", () -> new SimpleParticleType(false));


    public static void register(IEventBus modEventBus) {
        PARTICLES.register(modEventBus);
    }
}
