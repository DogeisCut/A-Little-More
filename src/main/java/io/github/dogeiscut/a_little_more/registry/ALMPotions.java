package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ALMPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, ALittleMore.MOD_ID);

    // Creates a potion with the Immunity effect that lasts for 3 minutes (3600 ticks)
    public static final Holder<Potion> IMMUNITY = POTIONS.register("immunity",
            () -> new Potion(new MobEffectInstance(ALMMobEffects.IMMUNITY, 3600)));

    public static void register(@NotNull IEventBus modEventBus) {
        POTIONS.register(modEventBus);
    }
}