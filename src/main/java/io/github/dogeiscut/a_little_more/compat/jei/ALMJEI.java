package io.github.dogeiscut.a_little_more.compat.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.compat.create.FanSeepingCategory;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class ALMJEI implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ALittleMore.id("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new SeepTransformationRecipeCategory(helper)
        );

        // TODO: use actual double icon https://github.com/Creators-of-Create/Create/blob/0924e93639ad5f61cfc39a221d909e16f2893df1/src/main/java/com/simibubi/create/compat/jei/CreateJEI.java#L165
        if (ModList.get().isLoaded("create")) {
            registration.addRecipeCategories(
                    new FanSeepingCategory(new CreateRecipeCategory.Info<>(
                            FanSeepingCategory.RECIPE_TYPE,
                            Component.translatable("a_little_more.recipe.fan_seeping"),
                            helper.createBlankDrawable(178, 72),
                            helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ALMFluids.SEEP.bucket().get())),
                            ALMJEI::getSeepTransformationRecipeHolders,
                            List.of(ProcessingViaFanCategory.getFan("fan_seeping"))
                    ))
            );
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<RecipeHolder<SeepTransformationRecipe>> holders = getSeepTransformationRecipeHolders();

        registration.addRecipes(
                SeepTransformationRecipeCategory.RECIPE_TYPE,
                holders.stream().map(RecipeHolder::value).toList()
        );

        if (ModList.get().isLoaded("create")) {
            registration.addRecipes(FanSeepingCategory.RECIPE_TYPE, holders);
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

        if (ModList.get().isLoaded("create")) {
            registration.addRecipeCatalyst(
                    ProcessingViaFanCategory.getFan("fan_seeping").get(),
                    FanSeepingCategory.RECIPE_TYPE
            );
        }
    }

    private static List<RecipeHolder<SeepTransformationRecipe>> getSeepTransformationRecipeHolders() {
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager recipeManager = null;

        if (minecraft.level != null) {
            recipeManager = minecraft.level.getRecipeManager();
        } else if (minecraft.getConnection() != null) {
            recipeManager = minecraft.getConnection().getRecipeManager();
        }

        if (recipeManager == null) {
            return Collections.emptyList();
        }

        return recipeManager.getAllRecipesFor(ALMRecipes.SEEP_TRANSFORMATION_TYPE.get());
    }
}