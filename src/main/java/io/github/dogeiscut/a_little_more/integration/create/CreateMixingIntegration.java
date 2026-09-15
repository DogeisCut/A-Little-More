package io.github.dogeiscut.a_little_more.integration.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

// TODO: broken :(
// TODO: JEI

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class CreateMixingIntegration {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener((barrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) -> barrier.wait(null).thenRunAsync(() -> {
            injectMixingRecipes(event.getServerResources().getRecipeManager());
        }, gameExecutor));
    }

    private static void injectMixingRecipes(RecipeManager recipeManager) {
        ALittleMore.LOGGER.info("[A Little More] Howdy! I'm Injecty, Injecty the injector!");
        List<RecipeHolder<SeepTransformationRecipe>> seepRecipes =
                recipeManager.getAllRecipesFor(ALMRecipes.SEEP_TRANSFORMATION_TYPE.get());

        List<RecipeHolder<?>> newMixingRecipes = new ArrayList<>();
        for (RecipeHolder<?> holder : recipeManager.getRecipes()) {
            if (!holder.id().getPath().startsWith("create_mixing/")) {
                newMixingRecipes.add(holder);
            }
        }

        for (RecipeHolder<SeepTransformationRecipe> holder : seepRecipes) {
            SeepTransformationRecipe recipe = holder.value();
            ResourceLocation mixingId = ResourceLocation.fromNamespaceAndPath(ALittleMore.MOD_ID, "create_mixing/" + holder.id().getPath());

            FluidStack seep100mb = new FluidStack(ALMFluids.SEEP.still().get(), 100);

            MixingRecipe mixingRecipe = new StandardProcessingRecipe.Builder<>(
                    MixingRecipe::new, mixingId)
                    .require(recipe.ingredient())
                    .require(SizedFluidIngredient.of(seep100mb.getFluid(), 100))
                    .output(recipe.result())
                    .output(seep100mb)
                    .requiresHeat(HeatCondition.NONE)
                    .build();

            newMixingRecipes.add(new RecipeHolder<>(mixingId, mixingRecipe));
        }

        recipeManager.replaceRecipes(newMixingRecipes);
    }
}

