package io.github.dogeiscut.a_little_more.integration.jei;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipeCategory;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class ALMJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ALittleMore.id("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new SeepTransformationRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager recipeManager = null;

        if (minecraft.level != null) {
            recipeManager = minecraft.level.getRecipeManager();
        } else if (minecraft.getConnection() != null) {
            recipeManager = minecraft.getConnection().getRecipeManager();
        }

        if (recipeManager != null) {
            List recipes = recipeManager
                    .getAllRecipesFor(ALMRecipes.SEEP_TRANSFORMATION_TYPE.get())
                    .stream()
                    .map(RecipeHolder::value)
                    .toList();

            registration.addRecipes(SeepTransformationRecipeCategory.RECIPE_TYPE, recipes);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ALMFluids.SEEP.bucket().get()),
                SeepTransformationRecipeCategory.RECIPE_TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(ALMFluids.SEEP.block().get()),
                SeepTransformationRecipeCategory.RECIPE_TYPE
        );
    }
}