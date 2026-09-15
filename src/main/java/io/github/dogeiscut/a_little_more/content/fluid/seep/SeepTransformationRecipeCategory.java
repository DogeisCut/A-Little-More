package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SeepTransformationRecipeCategory implements IRecipeCategory<SeepTransformationRecipe> {

    public static final RecipeType<SeepTransformationRecipe> RECIPE_TYPE =
            RecipeType.create(ALittleMore.MOD_ID, "seep_transformation", SeepTransformationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public SeepTransformationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 38);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ALMFluids.SEEP.bucket().get()));
        this.title = Component.translatable("gui.a_little_more.category.seep_transformation");
    }

    @Override
    public @NotNull RecipeType<SeepTransformationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SeepTransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 10)
                .addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 10)
                .addItemStack(recipe.result());
    }
}