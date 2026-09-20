package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;


public final class ALMTags {

    private ALMTags() {
    }

    public static final class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_CELERIUM_TOOL = mod("incorrect_for_celerium_tool");

        public static final TagKey<Block> CELERIUM_ORES = mod("celerium_ores");

        public static final TagKey<Block> C_CELERIUM_ORES = common("ores/celerium");

        public static final TagKey<Block> C_CELERIUM_STORAGE_BLOCKS = common("storage_blocks/celerium");

        private Blocks() {
        }

        private static @NotNull TagKey<Block> mod(@NotNull String path) {
            return TagKey.create(Registries.BLOCK, ALittleMore.id(path));
        }

        private static @NotNull TagKey<Block> common(@NotNull String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }

    public static final class Items {
        public static final TagKey<Item> CELERIUM_ORES = mod("celerium_ores");
        public static final TagKey<Item> SEEP_TRANSFORMABLE_MUSIC_DISCS = mod("seep_transformable_music_discs");

        public static final TagKey<Item> C_CELERIUM_ORES = common("ores/celerium");
        public static final TagKey<Item> C_CELERIUM_STORAGE_BLOCKS = common("storage_blocks/celerium");
        public static final TagKey<Item> C_CELERIUM_GEMS = common("gems/celerium");
        public static final TagKey<Item> C_EMERALD_GEMS = common("gems/emerald");
        public static final TagKey<Item> C_EMERALD_NUGGETS = common("nuggets/emerald");
        public static final TagKey<Item> C_SEEP_BUCKETS = common("buckets/seep");

        private Items() {
        }

        private static @NotNull TagKey<Item> mod(@NotNull String path) {
            return TagKey.create(Registries.ITEM, ALittleMore.id(path));
        }

        private static @NotNull TagKey<Item> common(@NotNull String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }

    public static final class PatternBlockPatterns {
        public static final TagKey<PatternBlockPattern> NO_ITEM_REQUIRED = mod("no_item_required");

        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_FLOWER = mod("pattern_item/flower");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_CREEPER = mod("pattern_item/creeper");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_SKULL = mod("pattern_item/skull");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_MOJANG = mod("pattern_item/mojang");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_GLOBE = mod("pattern_item/globe");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_PIGLIN = mod("pattern_item/piglin");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_FLOW = mod("pattern_item/flow");
        public static final TagKey<PatternBlockPattern> PATTERN_ITEM_GUSTER = mod("pattern_item/guster");

        private PatternBlockPatterns() {
        }

        public static @NotNull TagKey<PatternBlockPattern> forBannerPatternTag(@NotNull TagKey<BannerPattern> bannerPatternTag) {
            return mod(bannerPatternTag.location().getPath());
        }

        private static @NotNull TagKey<PatternBlockPattern> mod(@NotNull String path) {
            return TagKey.create(PatternBlockPattern.REGISTRY_KEY, ALittleMore.id(path));
        }
    }

    public static final class Fluids {
        public static final TagKey<Fluid> SEEP = mod("seep");
        public static final TagKey<Fluid> C_SEEP = common("seep");

        private Fluids() {
        }

        private static @NotNull TagKey<Fluid> mod(@NotNull String path) {
            return TagKey.create(Registries.FLUID, ALittleMore.id(path));
        }

        private static @NotNull TagKey<Fluid> common(@NotNull String path) {
            return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }
}
