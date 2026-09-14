package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ALMAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, ALittleMore.MOD_ID);

    public static final Holder<Attribute> POST_ATTACK_INVULNERABILITY_TIME_MULTIPLIER = ATTRIBUTES.register("post_attack_invulnerability_time_multiplier", () -> new PercentageAttribute(
            "attributes.a_little_more.post_attack_invulnerability_time_multiplier",
            1.0d,
            0.0d,
            16.0d
    ));

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}
