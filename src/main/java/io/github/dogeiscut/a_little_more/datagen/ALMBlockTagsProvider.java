package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ALMBlockTagsProvider extends BlockTagsProvider {

    public ALMBlockTagsProvider(@NotNull PackOutput output,
                                @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ALMBlocks.BLOCKS.getEntries().forEach(holder -> auto(holder.get()));

        ALMDatagen.BlockFamilies.SEEPSTONE.allBlocks().forEach(this::pickaxe);
        ALMDatagen.BlockFamilies.POLISHED_SEEPSTONE.allBlocks().forEach(this::pickaxe);
        ALMDatagen.BlockFamilies.SEEPSTONE_BRICKS.allBlocks().forEach(this::pickaxe);
        ALMDatagen.BlockFamilies.SEEPSTONE_TILES.allBlocks().forEach(this::pickaxe);

        ALMDatagen.BlockFamilies.SIMPLE_CUBES.forEach(this::pickaxe);
        ALMDatagen.BlockFamilies.ORES.forEach(this::pickaxe);

        ALMDatagen.BlockFamilies.PICKAXE_MINEABLE.forEach(this::pickaxe);
        ALMDatagen.BlockFamilies.AXE_MINEABLE.forEach(this::axe);
        ALMDatagen.BlockFamilies.SHOVEL_MINEABLE.forEach(this::shovel);
        ALMDatagen.BlockFamilies.HOE_MINEABLE.forEach(this::hoe);
        ALMDatagen.BlockFamilies.SWORD_EFFICIENT.forEach(this::sword);

        ALMDatagen.BlockFamilies.NEEDS_IRON_TOOL.forEach(this::ironTool);

        ore(ALMBlocks.CELERIUM_ORE.get(), Tags.Blocks.ORES_IN_GROUND_STONE);
        ore(ALMBlocks.DEEPSLATE_CELERIUM_ORE.get(), Tags.Blocks.ORES_IN_GROUND_DEEPSLATE);

        addAll(ALMTags.Blocks.CELERIUM_ORES, ALMDatagen.BlockFamilies.ORES);
        addAll(ALMTags.Blocks.C_CELERIUM_ORES, ALMDatagen.BlockFamilies.ORES);

        storageBlock(ALMBlocks.CELERIUM_BLOCK.get(), ALMTags.Blocks.C_CELERIUM_STORAGE_BLOCKS);
        tag(BlockTags.BEACON_BASE_BLOCKS).add(ALMBlocks.CELERIUM_BLOCK.get());

        tag(ALMTags.Blocks.INCORRECT_FOR_CELERIUM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
    }

    private void auto(Block block) {
        if (block instanceof SlabBlock) {
            tag(BlockTags.SLABS).add(block);
        }
        if (block instanceof StairBlock) {
            tag(BlockTags.STAIRS).add(block);
        }
        if (block instanceof WallBlock) {
            tag(BlockTags.WALLS).add(block);
        }
    }

    private void pickaxe(@NotNull Block block) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
    }

    private void axe(@NotNull Block block) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(block);
    }

    private void shovel(@NotNull Block block) {
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block);
    }

    private void hoe(@NotNull Block block) {
        tag(BlockTags.MINEABLE_WITH_HOE).add(block);
    }

    private void sword(@NotNull Block block) {
        tag(BlockTags.SWORD_EFFICIENT).add(block);
    }

    private void ironTool(@NotNull Block block) {
        tag(BlockTags.NEEDS_IRON_TOOL).add(block);
    }

    private void ore(@NotNull Block block, @NotNull TagKey<Block> inGround) {
        tag(Tags.Blocks.ORES).add(block);
        tag(Tags.Blocks.ORE_RATES_SINGULAR).add(block);
        tag(inGround).add(block);
    }

    private void storageBlock(@NotNull Block block, @NotNull TagKey<Block> specific) {
        tag(Tags.Blocks.STORAGE_BLOCKS).add(block);
        tag(specific).add(block);
    }

    private void addAll(@NotNull TagKey<Block> key, @NotNull List<Block> blocks) {
        var builder = tag(key);
        blocks.forEach(builder::add);
    }
}
