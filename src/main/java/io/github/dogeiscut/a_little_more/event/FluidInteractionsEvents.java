package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluidTypes;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class FluidInteractionsEvents {

    // TODO: this doesnt go here, move to another class
    @SubscribeEvent
    public static void onClientSetup(@NotNull FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ALMFluids.SEEP.still().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ALMFluids.SEEP.flowing().get(), RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(@NotNull FMLCommonSetupEvent event) {
        event.enqueueWork(() -> FluidInteractionRegistry.addInteraction(
                ALMFluidTypes.SEEP.get(),
                new FluidInteractionRegistry.InteractionInformation(
                        (level, currentPos, relativePos, currentState) ->
                                !level.getFluidState(relativePos).isEmpty() && !level.getFluidState(relativePos).is(ALMTags.Fluids.SEEP),

                        ALMBlocks.SEEPSTONE.get().defaultBlockState()
                )
        ));
    }
}