package io.github.dogeiscut.a_little_more.compat.create;

import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import mezz.jei.api.recipe.RecipeType;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

public class FanSeepingCategory extends ProcessingViaFanCategory<SeepTransformationRecipe> {

    public static final RecipeType<RecipeHolder<SeepTransformationRecipe>> RECIPE_TYPE =
            RecipeType.createRecipeHolderType(ALittleMore.id("fan_seeping"));

    public FanSeepingCategory(@NotNull Info<SeepTransformationRecipe> info) {
        super(info);
    }

    @Override
    protected @NotNull AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(@NotNull GuiGraphics graphics) {
        GuiGameElement.of(ALMFluids.SEEP.still().get())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);
    }

}