package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import io.github.dogeiscut.a_little_more.registry.ALMSounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ALMDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CELERIUM_ORE_CF = configuredFeature("celerium_ore");
    public static final ResourceKey<PlacedFeature> CELERIUM_ORE_PF = placedFeature("celerium_ore");
    public static final ResourceKey<BiomeModifier> CELERIUM_ORE_BM = biomeModifier("celerium_ore");

    public static final ResourceKey<JukeboxSong> JUST_A_LITTLE_MORE =
            ResourceKey.create(Registries.JUKEBOX_SONG, ALittleMore.id("just_a_little_more"));

    public static final RegistrySetBuilderHolder BUILDER = new RegistrySetBuilderHolder();

    public ALMDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER.build(), Set.of(ALittleMore.MOD_ID));
    }

    public static final class RegistrySetBuilderHolder {
        public net.minecraft.core.RegistrySetBuilder build() {
            return new net.minecraft.core.RegistrySetBuilder()
                    .add(Registries.CONFIGURED_FEATURE, ALMDatapackProvider::configuredFeatures)
                    .add(Registries.PLACED_FEATURE, ALMDatapackProvider::placedFeatures)
                    .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ALMDatapackProvider::biomeModifiers)
                    .add(Registries.JUKEBOX_SONG, ALMDatapackProvider::jukeboxSongs);
        }
    }

    private static void configuredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        RuleTest stone = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslate = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        ctx.register(CELERIUM_ORE_CF, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                List.of(
                        OreConfiguration.target(stone, ALMBlocks.CELERIUM_ORE.get().defaultBlockState()),
                        OreConfiguration.target(deepslate, ALMBlocks.DEEPSLATE_CELERIUM_ORE.get().defaultBlockState())
                ),
                6,
                0.3F
        )));
    }

    private static void placedFeatures(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> features = ctx.lookup(Registries.CONFIGURED_FEATURE);

        ctx.register(CELERIUM_ORE_PF, new PlacedFeature(
                features.getOrThrow(CELERIUM_ORE_CF),
                List.of(
                        CountPlacement.of(7),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)),
                        BiomeFilter.biome()
                )));
    }

    private static void biomeModifiers(BootstrapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> features = ctx.lookup(Registries.PLACED_FEATURE);

        ctx.register(CELERIUM_ORE_BM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(features.getOrThrow(CELERIUM_ORE_PF)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static void jukeboxSongs(BootstrapContext<JukeboxSong> ctx) {
        ctx.register(JUST_A_LITTLE_MORE, new JukeboxSong(
                ALMSounds.JUST_A_LITTLE_MORE,
                Component.translatable("jukebox_song." + ALittleMore.MOD_ID + ".just_a_little_more"),
                125.0F,
                7
        ));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ALittleMore.id(name));
    }

    private static ResourceKey<PlacedFeature> placedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ALittleMore.id(name));
    }

    private static ResourceKey<BiomeModifier> biomeModifier(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ALittleMore.id(name));
    }

}
