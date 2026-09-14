package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ALMCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ALittleMore.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> A_LITTLE_MORE = CREATIVE_MODE_TABS.register(
            "a_little_more",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ALittleMore.MOD_ID))
                    .icon(() -> new ItemStack(ALMItems.OPOSSUM_TAIL.get()))
                    .displayItems((params, output) -> ALMItems.ITEMS.getEntries()
                            .forEach(holder -> output.accept(holder.get())))
                    .build()
    );

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
