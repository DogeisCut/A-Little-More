package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ALMRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ALittleMore.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ALittleMore.MOD_ID);

    public static final Supplier<RecipeType<SeepTransformationRecipe>> SEEP_TRANSFORMATION_TYPE =
            RECIPE_TYPES.register("seep_transformation", () -> new RecipeType<>() {
                @Override
                public @NotNull String toString() {
                    return ALittleMore.id("seep_transformation").toString();
                }
            });

    public static final Supplier<RecipeSerializer<SeepTransformationRecipe>> SEEP_TRANSFORMATION_SERIALIZER =
            RECIPE_SERIALIZERS.register("seep_transformation", () -> SeepTransformationRecipe.Serializer.INSTANCE);

    public static void register(@NotNull IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
