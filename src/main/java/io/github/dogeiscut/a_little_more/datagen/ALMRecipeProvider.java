package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ALMRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ALMRecipeProvider(@NotNull PackOutput output, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static @NotNull String path(@NotNull ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private static @NotNull String path(@NotNull Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private static @NotNull String criterionName(@NotNull ItemLike item) {
        return "has_" + path(item);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {

        ALMDatagen.BlockFamilies.getAllFamilies().forEach(family -> family(out, family, family.isStone()));
        progression(out, ALMDatagen.BlockFamilies.SEEPSTONE_PROGRESSION);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ALMBlocks.PATTERN_BLOCK, 8)
                .pattern("/O/")
                .pattern("O#O")
                .pattern("/O/")
                .define('#', ItemTags.PLANKS)
                .define('O', Items.PAINTING)
                .define('/', Items.STICK)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ALMBlocks.STAMPING_TABLE)
                .pattern("_(")
                .pattern("##")
                .define('#', ItemTags.PLANKS)
                .define('(', Items.BRUSH)
                .define('_', Items.SMOOTH_STONE_SLAB)
                .unlockedBy(criterionName(ALMBlocks.PATTERN_BLOCK), has(ALMBlocks.PATTERN_BLOCK))
                .save(out);

        oreSmelting(out, List.of(ALMBlocks.CELERIUM_ORE.get(), ALMBlocks.DEEPSLATE_CELERIUM_ORE.get()),
                ALMItems.CELERIUM_SHARD.get(), 1.0F, 200);

        storageBlock(out, ALMItems.CELERIUM_SHARD.get(), ALMBlocks.CELERIUM_BLOCK.get());

        toolSet(out, ALMItems.CELERIUM_SHARD.get(),
                ALMItems.CELERIUM_SWORD.get(), ALMItems.CELERIUM_PICKAXE.get(), ALMItems.CELERIUM_AXE.get(),
                ALMItems.CELERIUM_SHOVEL.get(), ALMItems.CELERIUM_HOE.get());

        armorSet(out, ALMItems.CELERIUM_SHARD.get(),
                ALMItems.CELERIUM_HELMET.get(), ALMItems.CELERIUM_CHESTPLATE.get(),
                ALMItems.CELERIUM_LEGGINGS.get(), ALMItems.CELERIUM_BOOTS.get());

        nuggetPair(out, ALMItems.EMERALD_NUGGET.get(), Items.EMERALD);

        seepTransformation(out, Items.ENDER_PEARL, ALMItems.ENSEEPENED_PEARL.get());
        seepTransformation(out, Items.COBBLESTONE, Items.END_STONE);
        seepTransformation(out, Items.WATER_BUCKET, ALMFluids.SEEP.bucket().get());
        seepTransformation(out, Items.AMETHYST_SHARD, ALMItems.SEEP_CRYSTAL.get());
        seepTransformation(out, Items.AMETHYST_CLUSTER, ALMBlocks.SEEP_CRYSTAL_CLUSTER.get().asItem());
        seepTransformation(out, Items.STONE, ALMBlocks.SEEPSTONE.get().asItem());
        seepTransformation(out, ALMTags.Items.SEEP_TRANSFORMABLE_MUSIC_DISCS, ALMItems.MUSIC_DISC_JUST_A_LITTLE_MORE.get());
    }

    private void family(@NotNull RecipeOutput out, @NotNull ALMBlockFamily almFamily, boolean isStone) {
        BlockFamily family = almFamily.vanilla();
        if (!family.shouldGenerateRecipe()) return;
        Block base = family.getBaseBlock();

        family.getVariants().forEach((variant, block) -> {
            if (variant == BlockFamily.Variant.SLAB) {
                slabRecipe(out, block, base);
                if (isStone) stonecut(out, block, base, 2);
            } else if (variant == BlockFamily.Variant.STAIRS) {
                stairsRecipe(out, block, base);
                if (isStone) stonecut(out, block, base, 1);
            } else if (variant == BlockFamily.Variant.WALL) {
                wallRecipe(out, block, base);
                if (isStone) stonecut(out, block, base, 1);
            } else if (variant == BlockFamily.Variant.CHISELED) {
                Block slab = family.getVariants().get(BlockFamily.Variant.SLAB);
                if (slab != null) {
                    chiseledRecipe(out, block, slab);
                }
                if (isStone) stonecut(out, block, base, 1);
            } else if (variant == BlockFamily.Variant.POLISHED) {

            } else {
                ALittleMore.LOGGER.warn(
                        "[A Little More datagen] No recipe generator wired up for block family variant {} on {}",
                        variant, BuiltInRegistries.BLOCK.getKey(block));
            }
        });

        if (almFamily.hasPillar()) {
            // this is never null but INTELLIJ won't stop complaining
            assert almFamily.pillar() != null;
            pillarRecipe(out, almFamily.pillar(), base);
            if (isStone) stonecut(out, almFamily.pillar(), base, 1);
        }
    }

    private void progression(@NotNull RecipeOutput out, @NotNull List<ALMBlockFamily> chain) {
        for (int i = 0; i < chain.size(); i++) {
            Block earlierBase = chain.get(i).baseBlock();

            for (int j = i + 1; j < chain.size(); j++) {
                ALMBlockFamily later = chain.get(j);
                Block laterBase = later.baseBlock();

                if (j == i + 1) {
                    square4(out, laterBase, earlierBase);
                }
                stonecut(out, laterBase, earlierBase, 1);

                later.vanilla().getVariants().forEach((variant, block) -> {
                    if (variant == BlockFamily.Variant.SLAB) {
                        stonecut(out, block, earlierBase, 2);
                    } else if (variant == BlockFamily.Variant.STAIRS || variant == BlockFamily.Variant.WALL) {
                        stonecut(out, block, earlierBase, 1);
                    }
                });
            }
        }
    }

    public void slabRecipe(@NotNull RecipeOutput out, @NotNull ItemLike slab, @NotNull ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("###")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void stairsRecipe(@NotNull RecipeOutput out, @NotNull ItemLike stairs, @NotNull ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void wallRecipe(@NotNull RecipeOutput out, @NotNull ItemLike wall, @NotNull ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, wall, 6)
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void chiseledRecipe(@NotNull RecipeOutput out, @NotNull ItemLike slabs, @NotNull ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slabs, 1)
                .pattern("#")
                .pattern("#")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void pillarRecipe(@NotNull RecipeOutput out, @NotNull ItemLike blocks, @NotNull ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocks, 1)
                .pattern("#")
                .pattern("#")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void square4(@NotNull RecipeOutput out, @NotNull Block result, @NotNull Block material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .pattern("##")
                .pattern("##")
                .define('#', material)
                .unlockedBy(criterionName(material), has(material))
                .save(out);
    }

    public void stonecut(@NotNull RecipeOutput out, @NotNull Block result,
                         @NotNull Block material, int count) {
        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(criterionName(material), has(material))
                .save(out, ALittleMore.id(path(result) + "_from_" + path(material) + "_stonecutting"));
    }

    public void oreSmelting(@NotNull RecipeOutput out, @NotNull List<Block> ores,
                            Item result, float experience, int smeltTime) {
        for (Block ore : ores) {
            SimpleCookingRecipeBuilder
                    .smelting(Ingredient.of(ore), RecipeCategory.MISC, result, experience, smeltTime)
                    .unlockedBy(criterionName(ore), has(ore))
                    .save(out, ALittleMore.id(path(result) + "_from_smelting_" + path(ore)));

            SimpleCookingRecipeBuilder
                    .blasting(Ingredient.of(ore), RecipeCategory.MISC, result, experience, smeltTime / 2)
                    .unlockedBy(criterionName(ore), has(ore))
                    .save(out, ALittleMore.id(path(result) + "_from_blasting_" + path(ore)));
        }
    }

    public void storageBlock(@NotNull RecipeOutput out, @NotNull Item small, @NotNull Block block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', small)
                .unlockedBy(criterionName(small), has(small))
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, small, 9)
                .requires(block)
                .unlockedBy(criterionName(block), has(block))
                .save(out, ALittleMore.id(path(small) + "_from_" + path(block)));
    }

    public void nuggetPair(@NotNull RecipeOutput out, @NotNull Item nugget, @NotNull Item whole) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, whole)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', nugget)
                .unlockedBy(criterionName(nugget), has(nugget))
                .save(out, ALittleMore.id(path(whole) + "_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(whole)
                .unlockedBy(criterionName(whole), has(whole))
                .save(out, ALittleMore.id(path(nugget)));
    }

    public void toolSet(@NotNull RecipeOutput out, @NotNull Item material,
                        @NotNull Item sword, @NotNull Item pickaxe,
                        @NotNull Item axe, @NotNull Item shovel,
                        @NotNull Item hoe) {
        shaped(out, sword, material, "#", "#", "I");
        shaped(out, pickaxe, material, "###", " I ", " I ");
        shaped(out, axe, material, "##", "#I", " I");
        shaped(out, shovel, material, "#", "I", "I");
        shaped(out, hoe, material, "##", " I", " I");
    }

    public void armorSet(@NotNull RecipeOutput out, @NotNull Item material,
                         @NotNull Item helmet, @NotNull Item chestplate,
                         @NotNull Item leggings, @NotNull Item boots) {
        shaped(out, helmet, material, "###", "# #");
        shaped(out, chestplate, material, "# #", "###", "###");
        shaped(out, leggings, material, "###", "# #", "# #");
        shaped(out, boots, material, "# #", "# #");
    }

    private void shaped(@NotNull RecipeOutput out, @NotNull Item result, @NotNull Item material, String @NotNull ... pattern) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result);
        for (String row : pattern) {
            builder.pattern(row);
        }
        builder.define('#', material);
        if (String.join("", pattern).indexOf('I') >= 0) {
            builder.define('I', Items.STICK);
        }
        builder.unlockedBy(criterionName(material), has(material)).save(out);
    }

    public void seepTransformation(@NotNull RecipeOutput out, ItemLike input, @NotNull Item result) {
        ResourceLocation id = ALittleMore.id("seep_transformation/" + path(result));
        out.accept(id, new SeepTransformationRecipe(Ingredient.of(input), new ItemStack(result)), null);
    }

    public void seepTransformation(@NotNull RecipeOutput out, net.minecraft.tags.@NotNull TagKey<Item> inputTag, @NotNull Item result) {
        ResourceLocation id = ALittleMore.id("seep_transformation/" + path(result));
        out.accept(id, new SeepTransformationRecipe(Ingredient.of(inputTag), new ItemStack(result)), null);
    }
}
