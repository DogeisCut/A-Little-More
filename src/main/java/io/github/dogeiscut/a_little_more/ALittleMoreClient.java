package io.github.dogeiscut.a_little_more;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockColor;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFlipKey;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockGeometryLoader;
import io.github.dogeiscut.a_little_more.content.blocks.stamping_table.StampingTableScreen;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepBubbleParticle;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepBubblePopParticle;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepSplashParticle;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformParticle;
import io.github.dogeiscut.a_little_more.content.mobs.animals.opossum.OpossumModel;
import io.github.dogeiscut.a_little_more.content.mobs.animals.opossum.OpossumRenderer;
import io.github.dogeiscut.a_little_more.content.particle.ALMDripParticle;
import io.github.dogeiscut.a_little_more.registry.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, value = Dist.CLIENT)
public class ALittleMoreClient {
    public ALittleMoreClient(IEventBus modEventBus) {
    }

    @SubscribeEvent
    public static void onClientSetup(@NotNull FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ALMFluids.SEEP.still().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ALMFluids.SEEP.flowing().get(), RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerEntityRenderer(ALMEntities.ENSEEPENED_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ALMEntities.OPOSSUM.get(), OpossumRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ALMModelLayerLocations.OPOSSUM, OpossumModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleProviders(@NotNull RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ALMParticles.SEEP_BUBBLE.get(), SeepBubbleParticle.Provider::new);
        event.registerSpriteSet(ALMParticles.SEEP_BUBBLE_POP.get(), SeepBubblePopParticle.Provider::new);
        event.registerSpriteSet(ALMParticles.SEEP_TRANSFORM.get(), SeepTransformParticle.Provider::new);
        event.registerSpriteSet(ALMParticles.FALLING_SEEP.get(), ALMDripParticle.FallingSeepProvider::new);
        event.registerSpriteSet(ALMParticles.DRIPPING_SEEP.get(), ALMDripParticle.DrippingSeepProvider::new);
        event.registerSpriteSet(ALMParticles.FALLING_DRIPSTONE_SEEP.get(), ALMDripParticle.FallingDripstoneSeepProvider::new);
        event.registerSpriteSet(ALMParticles.DRIPPING_DRIPSTONE_SEEP.get(), ALMDripParticle.DrippingDripstoneSeepProvider::new);
        event.registerSpriteSet(ALMParticles.SEEP_SPLASH.get(), SeepSplashParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterGeometryLoaders(ModelEvent.@NotNull RegisterGeometryLoaders event) {
        event.register(PatternBlockGeometryLoader.ID, PatternBlockGeometryLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(@NotNull RegisterMenuScreensEvent event) {
        event.register(ALMMenuTypes.STAMPING_TABLE.get(), StampingTableScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.@NotNull Block event) {
        event.register(new PatternBlockColor(), ALMBlocks.PATTERN_BLOCK.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.@NotNull Item event) {
        event.register(new PatternBlockColor(), ALMBlocks.PATTERN_BLOCK.get());
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(@NotNull RegisterKeyMappingsEvent event) {
        event.register(PatternBlockFlipKey.FLIP);
    }
}

