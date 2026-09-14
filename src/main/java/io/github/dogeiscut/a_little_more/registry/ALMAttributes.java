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

    public static final Holder<Attribute> TARGET_HURT_TIME = ATTRIBUTES.register("target_hurt_time", () -> new PercentageAttribute(
            "attributes.a_little_more.target_hurt_time",
            1.0d,
            0.0d,
            16.0d
    ));

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}
