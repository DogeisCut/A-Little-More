package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ALMTags {

    private ALMTags() {
    }

    public static final class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_CELERIUM_TOOL =
                TagKey.create(BuiltInRegistries.BLOCK.key(), ALittleMore.id("incorrect_for_celerium_tool"));

        private Blocks() {
        }
    }

    public static final class Items {
        private Items() {
        }
    }
}
