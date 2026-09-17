package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class SeepTransformationEvents {

    private static final String SUBMERSION_KEY = "a_little_more:seep_submersion_ticks";
    private static final int TRANSFORM_TICKS = 60;

    // Only reason this isn't in SeepLiquidBlock.entityInside is I'm unsure how to handle items leaving the fluid (to properly remove the SUBMERSION_KEY)

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.@NotNull Post event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) {
            return;
        }

        Level level = itemEntity.level();
        if (level.isClientSide) {
            return;
        }

        ItemStack stack = itemEntity.getItem();
        if (stack.isEmpty()) {
            return;
        }

        CompoundTag data = itemEntity.getPersistentData();

        if (!isFullySubmergedInSeep(itemEntity)) {
            data.remove(SUBMERSION_KEY);
            return;
        }

        RecipeManager recipeManager = level.getRecipeManager();
        Optional<RecipeHolder<SeepTransformationRecipe>> match = recipeManager.getRecipeFor(
                ALMRecipes.SEEP_TRANSFORMATION_TYPE.get(), new SingleRecipeInput(stack), level);

        if (match.isEmpty()) {
            data.remove(SUBMERSION_KEY);
            return;
        }

        int ticks = data.getInt(SUBMERSION_KEY) + 1;

        if (ticks >= TRANSFORM_TICKS) {
            ItemStack result = match.get().value().assemble(new SingleRecipeInput(stack), level.registryAccess());
            result.setCount(stack.getCount());
            itemEntity.setItem(result);
            data.remove(SUBMERSION_KEY);
        } else {
            data.putInt(SUBMERSION_KEY, ticks);
        }
    }

    private static boolean isFullySubmergedInSeep(@NotNull ItemEntity itemEntity) {
        Level level = itemEntity.level();
        AABB box = itemEntity.getBoundingBox();
        BlockPos topPos = BlockPos.containing(itemEntity.getX(), box.maxY, itemEntity.getZ());
        FluidState fluidState = level.getFluidState(topPos);

        boolean isSeep = fluidState.is(ALMFluids.SEEP.still().get()) || fluidState.is(ALMFluids.SEEP.flowing().get());
        if (!isSeep) {
            return false;
        }

        double surfaceY = topPos.getY() + fluidState.getHeight(level, topPos);
        return box.maxY <= surfaceY;
    }
}