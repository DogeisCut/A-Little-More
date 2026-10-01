package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ALMParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, ALittleMore.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_BUBBLE =
            PARTICLES.register("seep_bubble", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_BUBBLE_POP =
            PARTICLES.register("seep_bubble_pop", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_TRANSFORM =
            PARTICLES.register("seep_transform", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_SEEP =
            PARTICLES.register("falling_seep", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_SEEP =
            PARTICLES.register("dripping_seep", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_DRIPSTONE_SEEP =
            PARTICLES.register("falling_dripstone_seep", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_DRIPSTONE_SEEP =
            PARTICLES.register("dripping_dripstone_seep", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_SPLASH =
            PARTICLES.register("seep_splash", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SEEP_FIRE_FLAME =
            PARTICLES.register("seep_fire_flame", () -> new SimpleParticleType(false));

    public static void register(@NotNull IEventBus modEventBus) {
        PARTICLES.register(modEventBus);
    }
}
