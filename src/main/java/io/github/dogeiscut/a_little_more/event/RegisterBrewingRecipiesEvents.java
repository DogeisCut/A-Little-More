package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import io.github.dogeiscut.a_little_more.registry.ALMPotions;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

// Note: Brewing recipes are registered on the GAME bus
@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class RegisterBrewingRecipiesEvents {

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addMix(
                Potions.AWKWARD,
                ALMItems.OPOSSUM_TAIL.get(),
                ALMPotions.IMMUNITY
        );
    }
}