package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluidTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FluidInteractions {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            FluidInteractionRegistry.addInteraction(
                    ALMFluidTypes.SEEP.get(),
                    new FluidInteractionRegistry.InteractionInformation(
                            (level, currentPos, relativePos, currentState) ->
                                    !level.getFluidState(relativePos).isEmpty(),

                            ALMBlocks.SEEPSTONE.get().defaultBlockState()
                    )
            );

//            FluidInteractionRegistry.addInteraction(
//                    NeoForgeMod.WATER_TYPE.value(),
//                    new FluidInteractionRegistry.InteractionInformation(
//                            (level, currentPos, relativePos, currentState) ->
//                                    level.getFluidState(relativePos).is(ALMFluids.SEEP.flowing().get()),
//
//                            ALMBlocks.SEEPSTONE.get().defaultBlockState()
//                    )
//            );
//
//            FluidInteractionRegistry.addInteraction(
//                    NeoForgeMod.LAVA_TYPE.value(),
//                    new FluidInteractionRegistry.InteractionInformation(
//                            (level, currentPos, relativePos, currentState) ->
//                                    level.getFluidState(relativePos).is(ALMFluids.SEEP.flowing().get()),
//
//                            ALMBlocks.SEEPSTONE.get().defaultBlockState()
//                    )
//            );
        });
    }
}