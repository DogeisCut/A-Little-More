package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;


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

        private static TagKey<Block> mod(String path) {
            return TagKey.create(Registries.BLOCK, ALittleMore.id(path));
        }

        private static TagKey<Block> common(String path) {
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

        private static TagKey<Item> mod(String path) {
            return TagKey.create(Registries.ITEM, ALittleMore.id(path));
        }

        private static TagKey<Item> common(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }

    public static final class Fluids {
        public static final TagKey<Fluid> SEEP = mod("seep");
        public static final TagKey<Fluid> C_SEEP = common("seep");

        private Fluids() {
        }

        private static TagKey<Fluid> mod(String path) {
            return TagKey.create(Registries.FLUID, ALittleMore.id(path));
        }

        private static TagKey<Fluid> common(String path) {
            return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }
}
