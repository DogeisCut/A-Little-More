package io.github.dogeiscut.a_little_more.content.mob_effects;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMMobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)

public class ImmunityEvents {
    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.@NotNull Applicable event) {
        if (event.getEntity().hasEffect(ALMMobEffects.IMMUNITY) && !event.getEffectInstance().is(ALMMobEffects.IMMUNITY)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}


