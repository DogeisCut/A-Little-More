package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.registry.ALMEntities;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ALMEntityLootProvider implements LootTableSubProvider {

    protected final HolderLookup.Provider registries;

    public ALMEntityLootProvider(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        // even with datagen writing loot tables sucks BUTT
        consumer.accept(
                ALMEntities.OPOSSUM.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceWithEnchantedBonusCondition
                                        .randomChanceAndLootingBoost(this.registries, 0.15f, 0.015f))
                                .add(LootItem.lootTableItem(ALMItems.OPOSSUM_TAIL.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
                                        .apply(EnchantedCountIncreaseFunction
                                                .lootingMultiplier(this.registries, UniformGenerator.between(0.0f, 1.0f)))))
        );
    }
}