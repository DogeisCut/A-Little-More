package io.github.dogeiscut.a_little_more;

import com.mojang.logging.LogUtils;
import io.github.dogeiscut.a_little_more.registry.ALMArmorMaterials;
import io.github.dogeiscut.a_little_more.registry.ALMCreativeTabs;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
@Mod(ALittleMore.MOD_ID)
public class ALittleMore {

    public static final String MOD_ID = "a_little_more";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ALittleMore(IEventBus modEventBus, ModContainer modContainer) {

//        ALMDataComponents.register(modEventBus);
//        ALMAttributes.register(modEventBus);
        ALMArmorMaterials.register(modEventBus);
        ALMItems.register(modEventBus);
//        ALMBlocks.register(modEventBus);
//        ALMBlockEntities.register(modEventBus);
//        ALMEntities.register(modEventBus);
//        ALMParticles.register(modEventBus);
//        ALMSounds.register(modEventBus);
        ALMCreativeTabs.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
