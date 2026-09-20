package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
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
        ALMDatagen.BlockFamilies.getAllFamilies().forEach(family -> family.allBlocks().forEach(this::selfDrop));

        ALMDatagen.BlockFamilies.SIMPLE_CUBES.forEach(this::selfDrop);
        ALMDatagen.BlockFamilies.AXE_MINEABLE.forEach(this::selfDrop);

        add(ALMBlocks.PATTERN_BLOCK.get(), block -> LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(block)
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                .include(ALMDataComponents.PATTERN_BLOCK_FACES)
                                        )
                                )
                                .when(ExplosionCondition.survivesExplosion())
                )
        );

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
