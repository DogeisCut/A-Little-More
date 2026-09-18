package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlock;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockItem;
import io.github.dogeiscut.a_little_more.content.weapons.flail.FlailItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ALMBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ALittleMore.MOD_ID);

    public static final DeferredBlock<Block> SEEP_CRYSTAL_CLUSTER = simpleBlockWithItem("seep_crystal_cluster", BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER));

    public static final DeferredBlock<Block> SEEPSTONE = simpleBlockWithItem("seepstone", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<SlabBlock> SEEPSTONE_SLAB = blockWithItem("seepstone_slab", p -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<StairBlock> SEEPSTONE_STAIRS = blockWithItem("seepstone_stairs", p -> new StairBlock(SEEPSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<WallBlock> SEEPSTONE_WALL = blockWithItem("seepstone_wall", p -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<Block> SEEPSTONE_BRICKS = blockWithItem("seepstone_bricks", p -> new Block(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<SlabBlock> SEEPSTONE_BRICK_SLAB = blockWithItem("seepstone_brick_slab", p -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<StairBlock> SEEPSTONE_BRICK_STAIRS = blockWithItem("seepstone_brick_stairs", p -> new StairBlock(SEEPSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<WallBlock> SEEPSTONE_BRICK_WALL = blockWithItem("seepstone_brick_wall", p -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<Block> POLISHED_SEEPSTONE = blockWithItem("polished_seepstone", p -> new Block(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<SlabBlock> POLISHED_SEEPSTONE_SLAB = blockWithItem("polished_seepstone_slab", p -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<StairBlock> POLISHED_SEEPSTONE_STAIRS = blockWithItem("polished_seepstone_stairs", p -> new StairBlock(SEEPSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<WallBlock> POLISHED_SEEPSTONE_WALL = blockWithItem("polished_seepstone_wall", p -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<Block> CHISELED_SEEPSTONE = blockWithItem("chiseled_seepstone", p -> new Block(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<RotatedPillarBlock> SEEPSTONE_PILLAR = blockWithItem("seepstone_pillar", p -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<Block> SEEPSTONE_TILES = blockWithItem("seepstone_tiles", p -> new Block(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<SlabBlock> SEEPSTONE_TILE_SLAB = blockWithItem("seepstone_tile_slab", p -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<StairBlock> SEEPSTONE_TILE_STAIRS = blockWithItem("seepstone_tile_stairs", p -> new StairBlock(SEEPSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));
    public static final DeferredBlock<WallBlock> SEEPSTONE_TILE_WALL = blockWithItem("seepstone_tile_wall", p -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SEEPSTONE.get())));

    public static final DeferredBlock<Block> CELERIUM_BLOCK = simpleBlockWithItem("celerium_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> CELERIUM_ORE = simpleBlockWithItem("celerium_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE));
    public static final DeferredBlock<Block> DEEPSLATE_CELERIUM_ORE = simpleBlockWithItem("deepslate_celerium_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE));

    public static final DeferredBlock<Block> DASH_PAD = simpleBlockWithItem("dash_pad", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> LAUNCH_PAD = simpleBlockWithItem("launch_pad", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<PatternBlock> WHITE_PATTERN_BLOCK = patternBlock("white_pattern_block", DyeColor.WHITE, Blocks.WHITE_WOOL);
    public static final DeferredBlock<PatternBlock> ORANGE_PATTERN_BLOCK = patternBlock("orange_pattern_block", DyeColor.ORANGE, Blocks.ORANGE_WOOL);
    public static final DeferredBlock<PatternBlock> MAGENTA_PATTERN_BLOCK = patternBlock("magenta_pattern_block", DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
    public static final DeferredBlock<PatternBlock> LIGHT_BLUE_PATTERN_BLOCK = patternBlock("light_blue_pattern_block", DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
    public static final DeferredBlock<PatternBlock> YELLOW_PATTERN_BLOCK = patternBlock("yellow_pattern_block", DyeColor.YELLOW, Blocks.YELLOW_WOOL);
    public static final DeferredBlock<PatternBlock> LIME_PATTERN_BLOCK = patternBlock("lime_pattern_block", DyeColor.LIME, Blocks.LIME_WOOL);
    public static final DeferredBlock<PatternBlock> PINK_PATTERN_BLOCK = patternBlock("pink_pattern_block", DyeColor.PINK, Blocks.PINK_WOOL);
    public static final DeferredBlock<PatternBlock> GRAY_PATTERN_BLOCK = patternBlock("gray_pattern_block", DyeColor.GRAY, Blocks.GRAY_WOOL);
    public static final DeferredBlock<PatternBlock> LIGHT_GRAY_PATTERN_BLOCK = patternBlock("light_gray_pattern_block", DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
    public static final DeferredBlock<PatternBlock> CYAN_PATTERN_BLOCK = patternBlock("cyan_pattern_block", DyeColor.CYAN, Blocks.CYAN_WOOL);
    public static final DeferredBlock<PatternBlock> PURPLE_PATTERN_BLOCK = patternBlock("purple_pattern_block", DyeColor.PURPLE, Blocks.PURPLE_WOOL);
    public static final DeferredBlock<PatternBlock> BLUE_PATTERN_BLOCK = patternBlock("blue_pattern_block", DyeColor.BLUE, Blocks.BLUE_WOOL);
    public static final DeferredBlock<PatternBlock> BROWN_PATTERN_BLOCK = patternBlock("brown_pattern_block", DyeColor.BROWN, Blocks.BROWN_WOOL);
    public static final DeferredBlock<PatternBlock> GREEN_PATTERN_BLOCK = patternBlock("green_pattern_block", DyeColor.GREEN, Blocks.GREEN_WOOL);
    public static final DeferredBlock<PatternBlock> RED_PATTERN_BLOCK = patternBlock("red_pattern_block", DyeColor.RED, Blocks.RED_WOOL);
    public static final DeferredBlock<PatternBlock> BLACK_PATTERN_BLOCK = patternBlock("black_pattern_block", DyeColor.BLACK, Blocks.BLACK_WOOL);

    public static @NotNull DeferredBlock<Block> simpleBlock(@NotNull String name, BlockBehaviour.@NotNull Properties properties) {
        return BLOCKS.registerSimpleBlock(name, properties);
    }

    public static <B extends Block> @NotNull DeferredBlock<B> block(@NotNull String name, @NotNull Function<BlockBehaviour.Properties, B> factory, BlockBehaviour.@NotNull Properties properties) {
        return BLOCKS.registerBlock(name, factory, properties);
    }

    public static @NotNull DeferredBlock<Block> simpleBlockWithItem(@NotNull String name, BlockBehaviour.@NotNull Properties properties) {
        DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(name, properties);
        ALMItems.blockItem(block);
        return block;
    }

    public static <B extends Block> @NotNull DeferredBlock<B> blockWithItem(@NotNull String name, @NotNull Function<BlockBehaviour.Properties, B> factory) {
        DeferredBlock<B> block = BLOCKS.registerBlock(name, factory);
        ALMItems.blockItem(block);
        return block;
    }

    public static <B extends Block> @NotNull DeferredBlock<B> blockWithItem(@NotNull String name, @NotNull Function<BlockBehaviour.Properties, B> factory, Item.@NotNull Properties itemProperties) {
        DeferredBlock<B> block = BLOCKS.registerBlock(name, factory);
        ALMItems.blockItem(block, itemProperties);
        return block;
    }

    public static @NotNull DeferredBlock<PatternBlock> patternBlock(@NotNull String name, @NotNull DyeColor color, @NotNull Block base) {
        DeferredBlock<PatternBlock> block = BLOCKS.registerBlock(name, p -> new PatternBlock(color, BlockBehaviour.Properties.ofFullCopy(base)));
        ALMItems.item(name, properties -> new PatternBlockItem(block.get(),
                properties.component(
                        DataComponents.BANNER_PATTERNS,
                        BannerPatternLayers.EMPTY
                )
        ));
        return block;
    }

    public static void register(@NotNull IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
