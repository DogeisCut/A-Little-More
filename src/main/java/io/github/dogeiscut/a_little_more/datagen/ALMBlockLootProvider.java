package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster.SeepCrystalClusterBlock;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Supplier;

public class ALMBlockLootProvider extends BlockLootSubProvider {

    protected ALMBlockLootProvider(HolderLookup.@NotNull Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
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

        LootItemCondition.Builder fragileTrue =
                LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(SeepCrystalClusterBlock.FRAGILE, true));
        LootItemCondition.Builder brokenByPlayer =
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(EntityType.PLAYER));
        this.add(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(AlternativesEntry.alternatives(
                                        EmptyLootItem.emptyItem()
                                                .when(fragileTrue)
                                                .when(brokenByPlayer.invert()),
                                        LootItem.lootTableItem(block).when(this.hasSilkTouch()),
                                        LootItem.lootTableItem(ALMItems.SEEP_CRYSTAL.get())
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                                .when(fragileTrue),
                                        LootItem.lootTableItem(ALMItems.SEEP_CRYSTAL.get())
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))
                                ))
                                .when(ExplosionCondition.survivesExplosion())
                        )
        );

        selfDrop(ALMBlocks.SEEP_TORCH.get());
        selfDrop(ALMBlocks.WALL_SEEP_TORCH.get());

        dropOther(ALMBlocks.SEEP_CAULDRON.get(), Blocks.CAULDRON);
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
