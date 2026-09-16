package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

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

    public static DeferredBlock<Block> simpleBlock(String name, BlockBehaviour.Properties properties) {
        return BLOCKS.registerSimpleBlock(name, properties);
    }

    public static <B extends Block> DeferredBlock<B> block(String name, Function<BlockBehaviour.Properties, B> factory, BlockBehaviour.Properties properties) {
        return BLOCKS.registerBlock(name, factory, properties);
    }

    public static DeferredBlock<Block> simpleBlockWithItem(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(name, properties);
        ALMItems.blockItem(block);
        return block;
    }

    public static <B extends Block> DeferredBlock<B> blockWithItem(String name, Function<BlockBehaviour.Properties, B> factory) {
        DeferredBlock<B> block = BLOCKS.registerBlock(name, factory);
        ALMItems.blockItem(block);
        return block;
    }

    public static <B extends Block> DeferredBlock<B> blockWithItem(String name, Function<BlockBehaviour.Properties, B> factory, Item.Properties itemProperties) {
        DeferredBlock<B> block = BLOCKS.registerBlock(name, factory);
        ALMItems.blockItem(block, itemProperties);
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
