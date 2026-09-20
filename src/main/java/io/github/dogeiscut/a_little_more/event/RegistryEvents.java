package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.@NotNull NewRegistry event) {
        event.dataPackRegistry(
                PatternBlockPattern.REGISTRY_KEY,
                PatternBlockPattern.DIRECT_CODEC,
                PatternBlockPattern.DIRECT_CODEC
        );
    }
}