package io.github.dogeiscut.a_little_more.datagen;

import com.google.common.collect.Maps;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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

    public static final class ALMBlockFamilies {
        public static final List<Block> SIMPLE_CUBES = List.of(
                ALMBlocks.CELERIUM_BLOCK.get(),
                ALMBlocks.CELERIUM_ORE.get(),
                ALMBlocks.DEEPSLATE_CELERIUM_ORE.get()
        );
        public static final List<Block> NEEDS_IRON_TOOL = List.of(
                ALMBlocks.CELERIUM_BLOCK.get(),
                ALMBlocks.CELERIUM_ORE.get(),
                ALMBlocks.DEEPSLATE_CELERIUM_ORE.get()
        );
        public static final List<Block> ORES = List.of(
                ALMBlocks.CELERIUM_ORE.get(),
                ALMBlocks.DEEPSLATE_CELERIUM_ORE.get()
        );
        public static final List<Block> AXE_MINEABLE = List.of(
                ALMBlocks.DASH_PAD.get(),
                ALMBlocks.LAUNCH_PAD.get()
        );
        public static final List<Block> PICKAXE_MINEABLE_EXTRA = List.of(
                ALMBlocks.DASH_PAD.get(),
                ALMBlocks.LAUNCH_PAD.get()
        );
        public static final List<Block> PATTERN_BLOCKS = List.of(
                ALMBlocks.WHITE_PATTERN_BLOCK.get(),
                ALMBlocks.ORANGE_PATTERN_BLOCK.get(),
                ALMBlocks.MAGENTA_PATTERN_BLOCK.get(),
                ALMBlocks.LIGHT_BLUE_PATTERN_BLOCK.get(),
                ALMBlocks.YELLOW_PATTERN_BLOCK.get(),
                ALMBlocks.LIME_PATTERN_BLOCK.get(),
                ALMBlocks.PINK_PATTERN_BLOCK.get(),
                ALMBlocks.GRAY_PATTERN_BLOCK.get(),
                ALMBlocks.LIGHT_GRAY_PATTERN_BLOCK.get(),
                ALMBlocks.CYAN_PATTERN_BLOCK.get(),
                ALMBlocks.PURPLE_PATTERN_BLOCK.get(),
                ALMBlocks.BLUE_PATTERN_BLOCK.get(),
                ALMBlocks.BROWN_PATTERN_BLOCK.get(),
                ALMBlocks.GREEN_PATTERN_BLOCK.get(),
                ALMBlocks.RED_PATTERN_BLOCK.get(),
                ALMBlocks.BLACK_PATTERN_BLOCK.get()
        );
        private static final Map<Block, ALMBlockFamily> MAP = Maps.newHashMap();
        public static final ALMBlockFamily SEEPSTONE = register(familyBuilder(ALMBlocks.SEEPSTONE.get())
                .slab(ALMBlocks.SEEPSTONE_SLAB.get())
                .stairs(ALMBlocks.SEEPSTONE_STAIRS.get())
                .wall(ALMBlocks.SEEPSTONE_WALL.get())
                .chiseled(ALMBlocks.CHISELED_SEEPSTONE.get())
                .polished(ALMBlocks.POLISHED_SEEPSTONE.get())
                .pillar(ALMBlocks.SEEPSTONE_PILLAR.get())
                .stone()
                .getFamily());
        public static final ALMBlockFamily POLISHED_SEEPSTONE = register(familyBuilder(ALMBlocks.POLISHED_SEEPSTONE.get())
                .slab(ALMBlocks.POLISHED_SEEPSTONE_SLAB.get())
                .stairs(ALMBlocks.POLISHED_SEEPSTONE_STAIRS.get())
                .wall(ALMBlocks.POLISHED_SEEPSTONE_WALL.get())
                .stone()
                .getFamily());
        public static final ALMBlockFamily SEEPSTONE_BRICKS = register(familyBuilder(ALMBlocks.SEEPSTONE_BRICKS.get())
                .slab(ALMBlocks.SEEPSTONE_BRICK_SLAB.get())
                .stairs(ALMBlocks.SEEPSTONE_BRICK_STAIRS.get())
                .wall(ALMBlocks.SEEPSTONE_BRICK_WALL.get())
                .stone()
                .getFamily());
        public static final ALMBlockFamily SEEPSTONE_TILES = register(familyBuilder(ALMBlocks.SEEPSTONE_TILES.get())
                .slab(ALMBlocks.SEEPSTONE_TILE_SLAB.get())
                .stairs(ALMBlocks.SEEPSTONE_TILE_STAIRS.get())
                .wall(ALMBlocks.SEEPSTONE_TILE_WALL.get())
                .stone()
                .getFamily());
        public static final List<ALMBlockFamily> SEEPSTONE_PROGRESSION = List.of(
                SEEPSTONE, POLISHED_SEEPSTONE, SEEPSTONE_BRICKS, SEEPSTONE_TILES
        );

        private static ALMBlockFamily.@NotNull Builder familyBuilder(@NotNull Block baseBlock) {
            return new ALMBlockFamily.Builder(baseBlock);
        }

        private static @NotNull ALMBlockFamily register(@NotNull ALMBlockFamily family) {
            ALMBlockFamily existing = MAP.put(family.baseBlock(), family);
            if (existing != null) {
                throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(family.baseBlock()));
            }
            return family;
        }

        public static @NotNull Stream<ALMBlockFamily> getAllFamilies() {
            return MAP.values().stream();
        }
    }
}
