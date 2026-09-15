package io.github.dogeiscut.a_little_more.content.fluid.seep;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SeepTransformationRecipe(Item ingredient, Item result) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return input.item().is(ingredient);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return new ItemStack(result);
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return new ItemStack(result);
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
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("ingredient").forGetter(SeepTransformationRecipe::ingredient),
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("result").forGetter(SeepTransformationRecipe::ingredient)
                ).apply(instance, SeepTransformationRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, SeepTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.registry(Registries.ITEM), SeepTransformationRecipe::ingredient,
                ByteBufCodecs.registry(Registries.ITEM), SeepTransformationRecipe::result,
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
