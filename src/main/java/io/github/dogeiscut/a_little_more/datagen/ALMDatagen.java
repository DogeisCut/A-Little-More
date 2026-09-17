package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class ALMDatagen {

    @SubscribeEvent
    public static void gatherData(@NotNull GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        ALMItemModelProvider itemModels = new ALMItemModelProvider(output, helper);
        generator.addProvider(event.includeClient(), itemModels);
        generator.addProvider(event.includeClient(), new ALMBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new ALMLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new ALMSoundProvider(output, helper));

        ALMBlockTagsProvider blockTags = new ALMBlockTagsProvider(output, lookup, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(),
                new ALMItemTagsProvider(output, lookup, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new ALMFluidTagsProvider(output, lookup, helper));

        generator.addProvider(event.includeServer(), new ALMRecipeProvider(output, lookup));
        generator.addProvider(event.includeServer(), ALMBlockLootProvider.create(output, lookup));
        generator.addProvider(event.includeServer(), new ALMDatapackProvider(output, lookup));

        generator.addProvider(event.includeClient(), new ALMParticleDescriptionProvider(output, helper));
    }
}
