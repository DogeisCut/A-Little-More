package io.github.dogeiscut.a_little_more.datagen;

import com.google.common.collect.Maps;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class ALMBlockFamilies {
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

    private static ALMBlockFamily.Builder familyBuilder(Block baseBlock) {
        return new ALMBlockFamily.Builder(baseBlock);
    }

    private static ALMBlockFamily register(ALMBlockFamily family) {
        ALMBlockFamily existing = MAP.put(family.baseBlock(), family);
        if (existing != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(family.baseBlock()));
        }
        return family;
    }

    public static Stream<ALMBlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}