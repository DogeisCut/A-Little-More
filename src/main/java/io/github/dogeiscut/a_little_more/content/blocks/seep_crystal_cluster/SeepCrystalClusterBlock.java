package io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster;

import io.github.dogeiscut.a_little_more.content.fluid.Fluidlogged;
import io.github.dogeiscut.a_little_more.content.fluid.SimpleFluidloggedBlock;
import io.github.dogeiscut.a_little_more.datagen.ALMBlockStateProperties;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class SeepCrystalClusterBlock extends Block implements SimpleFluidloggedBlock {
    public static final EnumProperty<Fluidlogged> FLUIDLOGGED = ALMBlockStateProperties.FLUIDLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty FRAGILE = BooleanProperty.create("fragile"); // just used to determine if it was placed by the world or not

    private final VoxelShape SHAPE_UP = Shapes.box(0.25, 0, 0.25, 0.75, 0.875, 0.75);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_UP;
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
}
