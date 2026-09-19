package io.github.dogeiscut.a_little_more;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockColor;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockGeometryLoader;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepBubbleParticle;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepBubblePopParticle;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMEntities;
import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, value = Dist.CLIENT)
public class ALittleMoreClient {
    public ALittleMoreClient(IEventBus modEventBus) {
    }

    // TODO: there's probably a better spot for all this. my goal with this file structure is to have related stuff as unseperated as possible

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerEntityRenderer(ALMEntities.ENSEEPENED_PEARL.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(@NotNull RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ALMParticles.SEEP_BUBBLE.get(), SeepBubbleParticle.Provider::new);
        event.registerSpriteSet(ALMParticles.SEEP_BUBBLE_POP.get(), SeepBubblePopParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(PatternBlockGeometryLoader.ID, PatternBlockGeometryLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(new PatternBlockColor(), ALMBlocks.PATTERN_BLOCK.get());
    }
}

