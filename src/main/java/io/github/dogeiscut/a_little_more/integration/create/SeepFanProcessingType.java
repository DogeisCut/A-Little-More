package io.github.dogeiscut.a_little_more.integration.create;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepTransformationRecipe;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO: JEI

public class SeepFanProcessingType implements FanProcessingType {

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        return fluidState.is(ALMTags.Fluids.SEEP);
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        return getMatchingRecipe(stack, level).isPresent();
    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        Optional<RecipeHolder<SeepTransformationRecipe>> recipe = getMatchingRecipe(stack, level);
        if (recipe.isPresent()) {
            return RecipeApplier.applyRecipeOn(level, stack, recipe.get().value(), false);
        }
        return Collections.emptyList();
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) == 0) {
            level.addParticle(
                    ParticleTypes.WITCH,
                    pos.x + (level.random.nextDouble() - 0.5D) * 0.5D,
                    pos.y + (level.random.nextDouble() - 0.5D) * 0.5D,
                    pos.z + (level.random.nextDouble() - 0.5D) * 0.5D,
                    0.0D, 0.05D, 0.0D
            );
        }
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0xDDB0FF, 0xB2B0FF, random.nextFloat()));
        particleAccess.setAlpha(.5f);
        if (random.nextFloat() < 1 / 16f)
            particleAccess.spawnExtraParticle(new BlockParticleOption(ParticleTypes.BLOCK, ALMFluids.SEEP.block().get().defaultBlockState()), .33f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        // TODO: apply levitation through SeepLiquidBlock lol
    }


    private Optional<RecipeHolder<SeepTransformationRecipe>> getMatchingRecipe(ItemStack stack, Level level) {
        return level.getRecipeManager()
                .getAllRecipesFor(ALMRecipes.SEEP_TRANSFORMATION_TYPE.get())
                .stream()
                .filter(r -> r.value().ingredient().test(stack))
                .findFirst();
    }
}