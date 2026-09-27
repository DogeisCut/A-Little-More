package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockDuplicateRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

// separate file because this is controlled by a feature flag
public class ALMPatternStampingRecipeProvider extends RecipeProvider {

    public ALMPatternStampingRecipeProvider(@NotNull PackOutput output, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static @NotNull String criterionName(net.minecraft.world.level.ItemLike item) {
        return "has_" + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {
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

        SpecialRecipeBuilder.special(PatternBlockDuplicateRecipe::new).save(out, ALittleMore.id("pattern_block_duplicate"));
    }
}
