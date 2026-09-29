package io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster;

import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
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

public class SeepCrystalClusterBlock extends Block implements SimpleWaterloggedBlock {
    public enum Fluidlogged implements StringRepresentable {
        EMPTY("empty", Fluids.EMPTY.defaultFluidState()),
        WATER("water", Fluids.WATER.getSource(false)),
        SEEP("seep", ALMFluids.SEEP.still().get().defaultFluidState());

        private final String name;
        private final FluidState fluidSource;

        Fluidlogged(String name, FluidState fluidSource) {
            this.name = name;
            this.fluidSource = fluidSource;
        }

        public String getName() {
            return name;
        }

        public FluidState getFluidSource() {
            return fluidSource;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public static Fluidlogged fromFluidState(FluidState fluidState) {
            if (fluidState.is(Fluids.WATER)) {
                return Fluidlogged.WATER;
            } else if (fluidState.is(ALMFluids.SEEP.still().get())) {
                return Fluidlogged.SEEP;
            }
            return Fluidlogged.EMPTY;
        }

        public ItemStack toBucketStack() {
            return switch (this) {
                case EMPTY -> ItemStack.EMPTY;
                case WATER -> new ItemStack(Items.WATER_BUCKET);
                case SEEP -> new ItemStack(ALMFluids.SEEP.bucket().get());
            };
        }
    }

    public static final EnumProperty<Fluidlogged> FLUIDLOGGED = EnumProperty.create("fluidlogged", Fluidlogged.class);
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

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return Fluidlogged.fromFluidState(fluid.defaultFluidState()) != Fluidlogged.EMPTY;
    }

    @Override
    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state, @NotNull FluidState fluidState) {
        if (!state.getValue(FLUIDLOGGED).getFluidSource().is(fluidState.getType()) && Fluidlogged.fromFluidState(fluidState) != Fluidlogged.EMPTY) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(FLUIDLOGGED, Fluidlogged.fromFluidState(fluidState)), 3);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull ItemStack pickupBlock(@Nullable Player player, @NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(FLUIDLOGGED) != Fluidlogged.EMPTY) {
            Fluidlogged oldFluidlogged = state.getValue(FLUIDLOGGED);
            level.setBlock(pos, state.setValue(FLUIDLOGGED, Fluidlogged.EMPTY), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }

            return oldFluidlogged.toBucketStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    // REALLY?
    // NO ARGUMENTS???
    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
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
