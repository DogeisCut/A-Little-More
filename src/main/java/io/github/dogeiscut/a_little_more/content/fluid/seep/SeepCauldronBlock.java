package io.github.dogeiscut.a_little_more.content.fluid.seep;

import com.mojang.serialization.MapCodec;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SeepCauldronBlock extends AbstractCauldronBlock {
    public static final CauldronInteraction.InteractionMap SEEP = CauldronInteraction.newInteractionMap("seep");
    public static final MapCodec<SeepCauldronBlock> CODEC = simpleCodec(SeepCauldronBlock::new);

    public SeepCauldronBlock(BlockBehaviour.@NotNull Properties properties) {
        super(properties, SEEP);
    }

    public static void addCauldronInteractions(CauldronInteraction.@NotNull InteractionMap interactionMap) {
        interactionMap.map().put(Items.LAVA_BUCKET, CauldronInteraction.FILL_LAVA);
        interactionMap.map().put(Items.WATER_BUCKET, CauldronInteraction.FILL_WATER);
        interactionMap.map().put(Items.POWDER_SNOW_BUCKET, CauldronInteraction.FILL_POWDER_SNOW);

        interactionMap.map().put(Items.BUCKET, (state, level, pos, player, hand, stack) ->
                CauldronInteraction.fillBucket(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        new ItemStack(ALMFluids.SEEP.bucket().get()),
                        other_state -> true,
                        ALMSounds.BUCKET_SEEP_FILL.get()
                )
        );
        interactionMap.map().put(ALMFluids.SEEP.bucket().get(),
                (state, level, pos, player, hand, stack) ->
                        CauldronInteraction.emptyBucket(
                                level,
                                pos,
                                player,
                                hand,
                                stack,
                                state,
                                ALMSounds.BUCKET_EMPTY_SEEP.get()
                        )
        );
        CauldronInteraction.EMPTY.map().put(ALMFluids.SEEP.bucket().get(),
                (state, level, pos, player, hand, stack) ->
                        CauldronInteraction.emptyBucket(
                                level,
                                pos,
                                player,
                                hand,
                                stack,
                                ALMBlocks.SEEP_CAULDRON.get().defaultBlockState(),
                                ALMSounds.BUCKET_EMPTY_SEEP.get()
                        )
        );
    }

    protected double getContentHeight(@NotNull BlockState state) {
        return 0.9375d;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockState state, @NotNull HitResult target, @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull Player player) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    protected @NotNull MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity)) {
            SeepLiquidBlock.levitate(level, entity);
        }
        if (!level.isClientSide && entity.isOnFire() && this.isEntityInsideContent(state, pos, entity)) {
            entity.clearFire();
        }
    }

    @Override
    public boolean isFull(@NotNull BlockState blockState) {
        return true;
    }

    protected int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return 3;
    }
}
