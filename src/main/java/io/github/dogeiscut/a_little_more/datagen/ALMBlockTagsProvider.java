package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ALMBlockTagsProvider extends BlockTagsProvider {

    public ALMBlockTagsProvider(PackOutput output,
                                CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ALMFamilies.SEEPSTONE_FAMILIES.forEach(set ->
                set.getAllBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block)));

        pickaxe(ALMFamilies.SIMPLE_CUBES);
        pickaxe(ALMFamilies.ORES);
        pickaxe(ALMFamilies.PICKAXE_MINEABLE_EXTRA);
        ALMFamilies.AXE_MINEABLE.forEach(b -> tag(BlockTags.MINEABLE_WITH_AXE).add(b));

        ALMFamilies.NEEDS_IRON_TOOL.forEach(b -> tag(BlockTags.NEEDS_IRON_TOOL).add(b));

        ore(ALMBlocks.CELERIUM_ORE.get(), Tags.Blocks.ORES_IN_GROUND_STONE);
        ore(ALMBlocks.DEEPSLATE_CELERIUM_ORE.get(), Tags.Blocks.ORES_IN_GROUND_DEEPSLATE);

        addAll(ALMTags.Blocks.CELERIUM_ORES, ALMFamilies.ORES);
        addAll(ALMTags.Blocks.C_CELERIUM_ORES, ALMFamilies.ORES);

        storageBlock(ALMBlocks.CELERIUM_BLOCK.get(), ALMTags.Blocks.C_CELERIUM_STORAGE_BLOCKS);
        tag(BlockTags.BEACON_BASE_BLOCKS).add(ALMBlocks.CELERIUM_BLOCK.get());

        tag(ALMTags.Blocks.INCORRECT_FOR_CELERIUM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
    }

    private void pickaxe(List<Block> blocks) {
        blocks.forEach(b -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(b));
    }

    private void ore(Block block, TagKey<Block> inGround) {
        tag(Tags.Blocks.ORES).add(block);
        tag(Tags.Blocks.ORE_RATES_SINGULAR).add(block);
        tag(inGround).add(block);
    }

    private void storageBlock(Block block, TagKey<Block> specific) {
        tag(Tags.Blocks.STORAGE_BLOCKS).add(block);
        tag(specific).add(block);
    }

    private void addAll(TagKey<Block> key, List<Block> blocks) {
        var builder = tag(key);
        blocks.forEach(b -> builder.add(b));
    }
}
