package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.mob_effects.ImmunityMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ALMMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, ALittleMore.MOD_ID);

    public static final Holder<MobEffect> IMMUNITY = MOB_EFFECTS.register("immunity", ImmunityMobEffect::new);

    public static void register(@NotNull IEventBus modEventBus) {
        MOB_EFFECTS.register(modEventBus);
    }
}
