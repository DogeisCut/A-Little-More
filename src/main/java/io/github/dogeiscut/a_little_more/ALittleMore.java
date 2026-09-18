package io.github.dogeiscut.a_little_more;

import com.mojang.logging.LogUtils;
import io.github.dogeiscut.a_little_more.compat.create.ALMCreate;
import io.github.dogeiscut.a_little_more.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(ALittleMore.MOD_ID)
@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class ALittleMore {

    public static final String MOD_ID = "a_little_more";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ALittleMore(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {

//        ALMDataComponents.register(modEventBus);
        ALMAttributes.register(modEventBus);
        ALMArmorMaterials.register(modEventBus);
        ALMItems.register(modEventBus);
        ALMBlocks.register(modEventBus);
        ALMFluids.register(modEventBus);
        ALMFluidTypes.register(modEventBus);
        ALMMobEffects.register(modEventBus);
        ALMPotions.register(modEventBus);
        ALMRecipes.register(modEventBus);
        ALMBlockEntities.register(modEventBus);
        ALMEntities.register(modEventBus);
        ALMParticles.register(modEventBus);
        ALMSounds.register(modEventBus);
        ALMCreativeTabs.register(modEventBus);

        if (ModList.get().isLoaded("create")) {
            ALMCreate.init(modEventBus);
        }

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {

    }

    public static @NotNull ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
