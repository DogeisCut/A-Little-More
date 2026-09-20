package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.stamping_table.StampingTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ALMMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, ALittleMore.MOD_ID);

    public static void register(@NotNull IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }    public static final DeferredHolder<MenuType<?>, MenuType<StampingTableMenu>> STAMPING_TABLE =
            MENU_TYPES.register("stamping_table",
                    () -> new MenuType<>(StampingTableMenu::new, FeatureFlags.DEFAULT_FLAGS));


}
