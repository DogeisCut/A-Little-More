package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ALMBlockLootProvider extends BlockLootSubProvider {

    protected ALMBlockLootProvider(HolderLookup.@NotNull Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    public static @NotNull LootTableProvider create(@NotNull PackOutput output,
                                                    @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(
                output,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(
                        ALMBlockLootProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider);
    }

    @Override
    protected void generate() {
        ALMDatagen.ALMBlockFamilies.getAllFamilies().forEach(family -> family.allBlocks().forEach(this::selfDrop));

        ALMDatagen.ALMBlockFamilies.SIMPLE_CUBES.forEach(this::selfDrop);
        ALMDatagen.ALMBlockFamilies.AXE_MINEABLE.forEach(this::selfDrop);

        selfDrop(ALMBlocks.PATTERN_BLOCK.get());

        oreDrop(ALMBlocks.CELERIUM_ORE.get(), ALMItems.CELERIUM_SHARD.get());
        oreDrop(ALMBlocks.DEEPSLATE_CELERIUM_ORE.get(), ALMItems.CELERIUM_SHARD.get());

        selfDrop(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get());
    }

    public void selfDrop(Block block) {
        if (block instanceof net.minecraft.world.level.block.SlabBlock) {
            add(block, this::createSlabItemTable);
        } else {
            dropSelf(block);
        }
    }

    public void oreDrop(@NotNull Block ore, @NotNull Item drop) {
        add(ore, block -> createOreDrop(block, drop));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ALMBlocks.BLOCKS.getEntries().stream()
                .map(Supplier::get)
                .map(b -> (Block) b)
                .filter(b -> b.getLootTable() != net.minecraft.world.level.storage.loot.BuiltInLootTables.EMPTY)
                .toList();
    }
}
