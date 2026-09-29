package io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster;

import io.github.dogeiscut.a_little_more.content.fluid.Fluidlogged;
import io.github.dogeiscut.a_little_more.content.fluid.SimpleFluidloggedBlock;
import io.github.dogeiscut.a_little_more.datagen.ALMBlockStateProperties;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import io.github.dogeiscut.a_little_more.registry.ALMSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class SeepCrystalClusterBlock extends Block implements EntityBlock, SimpleFluidloggedBlock {
    public static final EnumProperty<Fluidlogged> FLUIDLOGGED = ALMBlockStateProperties.FLUIDLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty FRAGILE = BooleanProperty.create("fragile"); // just used to determine if it was placed by the world or not

    private final VoxelShape SHAPE_UP = Shapes.box(0.25, 0, 0.25, 0.75, 0.875, 0.75);
    private final VoxelShape SHAPE_DOWN = Shapes.box(0.25, 0.125, 0.25, 0.75, 1, 0.75);
    private final VoxelShape SHAPE_NORTH = Shapes.box(0.25, 0.25, 0.125, 0.75, 0.75, 1);
    private final VoxelShape SHAPE_SOUTH = Shapes.box(0.25, 0.25, 0, 0.75, 0.75, 0.875);
    private final VoxelShape SHAPE_WEST = Shapes.box(0.125, 0.25, 0.25, 1, 0.75, 0.75);
    private final VoxelShape SHAPE_EAST = Shapes.box(0, 0.25, 0.25, 0.875, 0.75, 0.75);

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, direction);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!state.getValue(FRAGILE)) {
            if (level.getBlockEntity(pos) instanceof SeepCrystalClusterBlockEntity be) {
                if (stack.isEmpty() || !be.getContents().isEmpty()) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
                be.setContents(player.getItemInHand(hand));
                player.setItemInHand(hand, ItemStack.EMPTY);
                level.playSound(null, pos, ALMSoundEvents.SEEP_CRYSTAL_CLUSTER_ADD_ITEM.get(), SoundSource.BLOCKS);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (state.getValue(FRAGILE) && level instanceof ServerLevel serverLevel
                && serverLevel.getBlockEntity(pos) instanceof SeepCrystalClusterBlockEntity be) {
            Block.popResource(serverLevel, pos, be.getContents());
            be.setContents(ItemStack.EMPTY);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (state.getValue(FRAGILE) && level instanceof ServerLevel serverLevel) {
            this.popExperience(serverLevel, pos, level.random.nextInt(4) + 1);
        }
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !state.getValue(FRAGILE)
                && level instanceof ServerLevel serverLevel
                && serverLevel.getBlockEntity(pos) instanceof SeepCrystalClusterBlockEntity be) {
            Block.popResource(serverLevel, pos, be.getContents());
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    public SeepCrystalClusterBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.UP)
                .setValue(FLUIDLOGGED, Fluidlogged.EMPTY)
                .setValue(FRAGILE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FLUIDLOGGED, FRAGILE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(FLUIDLOGGED) != Fluidlogged.EMPTY) {
            FluidState fluidstate = level.getFluidState(pos);
            level.scheduleTick(pos, fluidstate.getType(), fluidstate.getType().getTickDelay(level));
        }

        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(FLUIDLOGGED, Fluidlogged.fromFluidState(fluidstate));
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(FLUIDLOGGED).getFluidSource();
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new SeepCrystalClusterBlockEntity(blockPos, blockState);
    }
}
