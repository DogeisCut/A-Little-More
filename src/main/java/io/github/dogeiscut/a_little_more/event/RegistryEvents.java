package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import io.github.dogeiscut.a_little_more.content.mobs.animals.opossum.OpossumEntity;
import io.github.dogeiscut.a_little_more.registry.*;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.@NotNull NewRegistry event) {
        event.dataPackRegistry(
                PatternBlockPattern.REGISTRY_KEY,
                PatternBlockPattern.DIRECT_CODEC,
                PatternBlockPattern.DIRECT_CODEC
        );
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ALMEntities.OPOSSUM.get(), OpossumEntity.createAttributes());
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(@NotNull RegisterBrewingRecipesEvent event) {
        event.getBuilder().addMix(
                Potions.AWKWARD,
                ALMItems.OPOSSUM_TAIL.get(),
                ALMPotions.IMMUNITY
        );
    }

    @SubscribeEvent
    public static void registerCauldronFluidContent(RegisterCauldronFluidContentEvent event) {
        event.register(ALMBlocks.SEEP_CAULDRON.get(), ALMFluids.SEEP.still().get(), FluidType.BUCKET_VOLUME, null);
    }
}