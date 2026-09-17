package io.github.dogeiscut.a_little_more;

import io.github.dogeiscut.a_little_more.registry.ALMEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ALittleMoreClient {
    public ALittleMoreClient(IEventBus modEventBus) {
    }

    // TODO: there's probably a better spot for this. my goal with this file structure is to have related stuff as unseperated as possible
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerEntityRenderer(ALMEntities.ENSEEPENED_PEARL.get(), ThrownItemRenderer::new);
    }
}

