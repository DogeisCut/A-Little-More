package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PatternBlockDuplicateRecipe extends CustomRecipe {
    public PatternBlockDuplicateRecipe(CraftingBookCategory category) {
        super(category);
    }

    private record CraftingMatch(ItemStack source, ItemStack receiver) {}

    @Nullable
    private CraftingMatch findMatch(CraftingInput input) {
        ItemStack source = null;
        ItemStack receiver = null;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (!(stack.getItem() instanceof PatternBlockItem)) {
                return null;
            }

            PatternBlockFaces faces = stack.get(ALMDataComponents.PATTERN_BLOCK_FACES);
            boolean hasFaces = faces != null && !faces.faces().isEmpty();

            if (hasFaces) {
                if (source != null) return null;
                source = stack;
            } else {
                if (receiver != null) return null;
                receiver = stack;
            }
        }

        return (source != null && receiver != null) ? new CraftingMatch(source, receiver) : null;
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        return findMatch(input) != null;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) {
        CraftingMatch match = findMatch(input);
        if (match == null) {
            return ItemStack.EMPTY;
        }

        ItemStack result = match.receiver().copyWithCount(1);
        PatternBlockFaces sourceFaces = match.source().get(ALMDataComponents.PATTERN_BLOCK_FACES);
        if (sourceFaces != null) {
            result.set(ALMDataComponents.PATTERN_BLOCK_FACES, sourceFaces);
        }

        return result;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < remaining.size(); ++i) {
            ItemStack stack = input.getItem(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.hasCraftingRemainingItem()) {
                remaining.set(i, stack.getCraftingRemainingItem());
            } else {
                PatternBlockFaces faces = stack.get(ALMDataComponents.PATTERN_BLOCK_FACES);
                if (faces != null && !faces.faces().isEmpty()) {
                    remaining.set(i, stack.copyWithCount(1));
                }
            }
        }

        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ALMRecipes.PATTERN_BLOCK_DUPLICATE.get();
    }
}
