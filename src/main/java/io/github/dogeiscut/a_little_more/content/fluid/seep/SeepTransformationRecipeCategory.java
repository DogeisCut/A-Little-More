package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.client.gui.Render3DHelper;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class SeepTransformationRecipeCategory implements IRecipeCategory<SeepTransformationRecipe> {

    public static final RecipeType<SeepTransformationRecipe> RECIPE_TYPE =
            RecipeType.create(ALittleMore.MOD_ID, "seep_transformation", SeepTransformationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotBackground;
    private final IDrawable arrow;

    public SeepTransformationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(140, 64);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ALMFluids.SEEP.bucket().get()));
        this.slotBackground = helper.getSlotDrawable();
        // TODO: find the actual arrow textures, this is just missing
        this.arrow = helper.createDrawable(ResourceLocation.fromNamespaceAndPath("jei", "textures/jei/gui/gui_vanilla.png"), 82, 128, 18, 17);
    }

    @Override
    public @NotNull RecipeType<SeepTransformationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("gui.a_little_more.category.seep_transformation");
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
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 22)
                .setBackground(slotBackground, -1, -1)
                .addIngredients(recipe.ingredient());

        builder.addSlot(RecipeIngredientRole.CATALYST, 62, 6)
                .setBackground(slotBackground, -1, -1)
                .addFluidStack(ALMFluids.SEEP.still().get(), 1000)
                .setFluidRenderer(1000L, false, 16, 16);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 22)
                .setBackground(slotBackground, -1, -1)
                .addItemStack(recipe.result());
    }

    @Override
    public void draw(SeepTransformationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 34, 22);
        arrow.draw(guiGraphics, 88, 22);

        Render3DHelper.render3DFluid(guiGraphics, new FluidStack(ALMFluids.SEEP.still().get(), 1000), 70, 48, 22.0f);

        ItemStack[] items = recipe.ingredient().getItems();
        if (items.length > 0) {
            Render3DHelper.render3DItem(guiGraphics, items[0], 70, 36, 35.0f, 0.45f, -15.0f, 15.0f);
        }
    }
}