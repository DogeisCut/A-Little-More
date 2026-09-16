package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.world.level.block.Block;

import java.util.List;

public final class ALMFamilies {

    private ALMFamilies() {}

    public static final ALMBlockFamily SEEPSTONE_TILES = new ALMBlockFamily.Builder(ALMBlocks.SEEPSTONE_TILES.get())
            .slab(ALMBlocks.SEEPSTONE_TILE_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_TILE_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_TILE_WALL.get())
            .build();

    public static final ALMBlockFamily SEEPSTONE_BRICKS = new ALMBlockFamily.Builder(ALMBlocks.SEEPSTONE_BRICKS.get())
            .slab(ALMBlocks.SEEPSTONE_BRICK_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_BRICK_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_BRICK_WALL.get())
            .derivative(SEEPSTONE_TILES)
            .build();

    public static final ALMBlockFamily POLISHED_SEEPSTONE = new ALMBlockFamily.Builder(ALMBlocks.POLISHED_SEEPSTONE.get())
            .slab(ALMBlocks.POLISHED_SEEPSTONE_SLAB.get())
            .stairs(ALMBlocks.POLISHED_SEEPSTONE_STAIRS.get())
            .wall(ALMBlocks.POLISHED_SEEPSTONE_WALL.get())
            .derivative(SEEPSTONE_BRICKS)
            .build();

    public static final ALMBlockFamily SEEPSTONE = new ALMBlockFamily.Builder(ALMBlocks.SEEPSTONE.get())
            .slab(ALMBlocks.SEEPSTONE_SLAB.get())
            .stairs(ALMBlocks.SEEPSTONE_STAIRS.get())
            .wall(ALMBlocks.SEEPSTONE_WALL.get())
            .chiseled(ALMBlocks.CHISELED_SEEPSTONE.get())
            .derivative(POLISHED_SEEPSTONE)
            .build();

    public static final List<ALMBlockFamily> SEEPSTONE_FAMILIES = List.of(SEEPSTONE, POLISHED_SEEPSTONE, SEEPSTONE_BRICKS);
    public static final List<ALMBlockFamily> ALL_FAMILIES = List.of(SEEPSTONE, POLISHED_SEEPSTONE, SEEPSTONE_BRICKS);

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
}
