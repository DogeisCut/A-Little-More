package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.weapons.flail.FlailItem;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ALMItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public ALMItemTagsProvider(@NotNull PackOutput output,
                               @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider,
                               @NotNull CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ALMItems.ITEMS.getEntries().forEach(holder -> autoTag(holder.get()));

        tag(ALMTags.Items.SEEP_TRANSFORMABLE_MUSIC_DISCS)
                .addTag(Tags.Items.MUSIC_DISCS)
                .remove(BuiltInRegistries.ITEM.getResourceKey(ALMItems.MUSIC_DISC_JUST_A_LITTLE_MORE.get()).orElseThrow());

        copy(ALMTags.Blocks.CELERIUM_ORES, ALMTags.Items.CELERIUM_ORES);
        copy(ALMTags.Blocks.C_CELERIUM_ORES, ALMTags.Items.C_CELERIUM_ORES);
        copy(ALMTags.Blocks.C_CELERIUM_STORAGE_BLOCKS, ALMTags.Items.C_CELERIUM_STORAGE_BLOCKS);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        add(Tags.Items.GEMS, ALMItems.CELERIUM_SHARD);
        add(ALMTags.Items.C_CELERIUM_GEMS, ALMItems.CELERIUM_SHARD);
        add(ItemTags.BEACON_PAYMENT_ITEMS, ALMItems.CELERIUM_SHARD);
        add(ItemTags.TRIM_MATERIALS, ALMItems.CELERIUM_SHARD);

        add(Tags.Items.NUGGETS, ALMItems.EMERALD_NUGGET);
        add(ALMTags.Items.C_EMERALD_NUGGETS, ALMItems.EMERALD_NUGGET);
        add(Tags.Items.GEMS, ALMItems.EMERALD_NUGGET);
        add(ALMTags.Items.C_EMERALD_GEMS, ALMItems.EMERALD_NUGGET);

        add(Tags.Items.ENDER_PEARLS, ALMItems.ENSEEPENED_PEARL);
        add(Tags.Items.MUSIC_DISCS, ALMItems.MUSIC_DISC_JUST_A_LITTLE_MORE);
        add(ALMTags.Items.C_SEEP_BUCKETS, ALMFluids.SEEP.bucket());
    }

    public void autoTag(Item item) {
        if (item instanceof SwordItem) {
            add(ItemTags.SWORDS, item);
            add(ItemTags.SWORD_ENCHANTABLE, item);
            add(ItemTags.SHARP_WEAPON_ENCHANTABLE, item);
            add(ItemTags.FIRE_ASPECT_ENCHANTABLE, item);
            weapon(item);
            tool(item);
        } else if (item instanceof PickaxeItem) {
            add(ItemTags.PICKAXES, item);
            add(ItemTags.CLUSTER_MAX_HARVESTABLES, item);
            add(Tags.Items.MINING_TOOL_TOOLS, item);
            mining(item);
            tool(item);
        } else if (item instanceof AxeItem) {
            add(ItemTags.AXES, item);
            add(ItemTags.SHARP_WEAPON_ENCHANTABLE, item);
            weapon(item);
            mining(item);
            tool(item);
        } else if (item instanceof ShovelItem) {
            add(ItemTags.SHOVELS, item);
            mining(item);
            tool(item);
        } else if (item instanceof HoeItem) {
            add(ItemTags.HOES, item);
            mining(item);
            tool(item);
        } else if (item instanceof FlailItem) {
            weapon(item);
            tool(item);
        }

        if (item instanceof ArmorItem armor) {
            add(Tags.Items.ARMORS, item);
            add(ItemTags.TRIMMABLE_ARMOR, item);
            add(ItemTags.ARMOR_ENCHANTABLE, item);
            add(ItemTags.EQUIPPABLE_ENCHANTABLE, item);
            durable(item);

            switch (armor.getType()) {
                case HELMET -> {
                    add(ItemTags.HEAD_ARMOR, item);
                    add(ItemTags.HEAD_ARMOR_ENCHANTABLE, item);
                }
                case CHESTPLATE -> {
                    add(ItemTags.CHEST_ARMOR, item);
                    add(ItemTags.CHEST_ARMOR_ENCHANTABLE, item);
                }
                case LEGGINGS -> {
                    add(ItemTags.LEG_ARMOR, item);
                    add(ItemTags.LEG_ARMOR_ENCHANTABLE, item);
                }
                case BOOTS -> {
                    add(ItemTags.FOOT_ARMOR, item);
                    add(ItemTags.FOOT_ARMOR_ENCHANTABLE, item);
                }
                default -> {
                }
            }
        }

        if (item instanceof BucketItem) {
            add(Tags.Items.BUCKETS, item);
        }
    }

    private void tool(@NotNull Item item) {
        add(Tags.Items.TOOLS, item);
        add(ItemTags.BREAKS_DECORATED_POTS, item);
        durable(item);
    }

    private void weapon(@NotNull Item item) {
        add(Tags.Items.MELEE_WEAPON_TOOLS, item);
        add(ItemTags.WEAPON_ENCHANTABLE, item);
    }

    private void mining(@NotNull Item item) {
        add(ItemTags.MINING_ENCHANTABLE, item);
        add(ItemTags.MINING_LOOT_ENCHANTABLE, item);
    }

    private void durable(@NotNull Item item) {
        add(ItemTags.DURABILITY_ENCHANTABLE, item);
        add(ItemTags.VANISHING_ENCHANTABLE, item);
        add(Tags.Items.ENCHANTABLES, item);
    }

    private void add(@NotNull TagKey<Item> key, @NotNull Item item) {
        tag(key).add(BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow());
    }

    private void add(@NotNull TagKey<Item> key, @NotNull Supplier<? extends Item> item) {
        add(key, item.get());
    }

    @SuppressWarnings("unused")
    private void add(@NotNull TagKey<Item> key, @NotNull DeferredBlock<?> block) {
        add(key, block.get().asItem());
    }
}
