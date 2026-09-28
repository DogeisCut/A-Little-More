package io.github.dogeiscut.a_little_more.content.imbuing;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SmithingImbuingRecipe implements SmithingRecipe {

    @Override
    public boolean isTemplateIngredient(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isBaseIngredient(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return null;
    }
}
