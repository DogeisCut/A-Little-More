package io.github.dogeiscut.a_little_more.datagen;

import com.google.common.collect.Maps;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class ALMBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily SEEPSTONE = familyBuilder(ALMBlocks.SEEPSTONE.get())
            .slab(ALMBlocks.SEEPSTONE_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_WALL.get())
            .chiseled(ALMBlocks.CHISELED_SEEPSTONE.get())
            .polished(ALMBlocks.POLISHED_SEEPSTONE.get())
            .getFamily();
    public static final BlockFamily POLISHED_SEEPSTONE = familyBuilder(ALMBlocks.POLISHED_SEEPSTONE.get())
            .slab(ALMBlocks.POLISHED_SEEPSTONE_SLAB.get())
            .stairs(ALMBlocks.POLISHED_SEEPSTONE_STAIRS.get())
            .wall(ALMBlocks.POLISHED_SEEPSTONE_WALL.get())
            .getFamily();
    public static final BlockFamily SEEPSTONE_BRICKS = familyBuilder(ALMBlocks.SEEPSTONE_BRICKS.get())
            .slab(ALMBlocks.SEEPSTONE_BRICK_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_BRICK_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_BRICK_WALL.get())
            .getFamily();
    public static final BlockFamily SEEPSTONE_TILES = familyBuilder(ALMBlocks.SEEPSTONE_TILES.get())
            .slab(ALMBlocks.SEEPSTONE_TILE_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_TILE_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_TILE_WALL.get())
            .getFamily();

    public static final List<Block> SIMPLE_CUBES = List.of(
            ALMBlocks.CHISELED_SEEPSTONE.get(),
            ALMBlocks.CELERIUM_BLOCK.get()
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

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
