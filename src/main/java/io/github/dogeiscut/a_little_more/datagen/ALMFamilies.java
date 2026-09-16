package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class ALMFamilies {

    private ALMFamilies() {}

    public record StoneSet(
            Block base,
            @Nullable SlabBlock slab,
            @Nullable StairBlock stairs,
            @Nullable WallBlock wall,
            @Nullable Block cutFrom
    ) {
        public List<Block> all() {
            List<Block> list = new ArrayList<>(4);
            list.add(base);
            if (slab != null) list.add(slab);
            if (stairs != null) list.add(stairs);
            if (wall != null) list.add(wall);
            return list;
        }

        public Block stonecutSource() {
            return cutFrom != null ? cutFrom : base;
        }
    }

    public static StoneSet set(Block base,
                               SlabBlock slab,
                               StairBlock stairs,
                               WallBlock wall,
                               @Nullable Block cutFrom) {
        return new StoneSet(base, slab, stairs, wall, cutFrom);
    }

    // Considering getting rid of stone sets since it has a ton of issues
    // 1. Vanilla stone sets aren't consistent, and neither will mine, it'd be more benifical to have control over individual blocks
    // 2. Polished blocks cut into brick blocks and their varients, brick blocks just cut into the varients, the base block cuts into every varient. This doesn't do any of those except the last

    public static final List<StoneSet> STONE_SETS = List.of(
            set(ALMBlocks.SEEPSTONE.get(), ALMBlocks.SEEPSTONE_SLAB.get(), ALMBlocks.SEEPSTONE_STAIRS.get(),
                    ALMBlocks.SEEPSTONE_WALL.get(), null),
            set(ALMBlocks.SEEPSTONE_BRICKS.get(), ALMBlocks.SEEPSTONE_BRICK_SLAB.get(), ALMBlocks.SEEPSTONE_BRICK_STAIRS.get(),
                    ALMBlocks.SEEPSTONE_BRICK_WALL.get(), ALMBlocks.SEEPSTONE.get()),
            set(ALMBlocks.POLISHED_SEEPSTONE.get(), ALMBlocks.POLISHED_SEEPSTONE_SLAB.get(), ALMBlocks.POLISHED_SEEPSTONE_STAIRS.get(),
                    ALMBlocks.POLISHED_SEEPSTONE_WALL.get(), ALMBlocks.SEEPSTONE.get())
    );

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
