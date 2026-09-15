package io.github.dogeiscut.a_little_more.content.fluid.seep;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SeepTransformationRecipe(Ingredient ingredient, ItemStack result) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredient);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return ALMRecipes.SEEP_TRANSFORMATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ALMRecipes.SEEP_TRANSFORMATION_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SeepTransformationRecipe> {

        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<SeepTransformationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(SeepTransformationRecipe::ingredient),
                        ItemStack.CODEC.fieldOf("result").forGetter(SeepTransformationRecipe::result)
                ).apply(instance, SeepTransformationRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, SeepTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, SeepTransformationRecipe::ingredient,
                ItemStack.STREAM_CODEC, SeepTransformationRecipe::result,
                SeepTransformationRecipe::new
        );

        @Override
        public @NotNull MapCodec<SeepTransformationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SeepTransformationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}