package io.github.dogeiscut.a_little_more.content.fluid;

import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public enum Fluidlogged implements StringRepresentable {
    EMPTY("empty", Fluids.EMPTY.defaultFluidState()),
    WATER("water", Fluids.WATER.getSource(false)),
    LAVA("lava", Fluids.LAVA.getSource(false)),
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
        } else if (fluidState.is(Fluids.LAVA)) {
            return Fluidlogged.LAVA;
        } else if (fluidState.is(ALMFluids.SEEP.still().get())) {
            return Fluidlogged.SEEP;
        }
        return Fluidlogged.EMPTY;
    }

    public ItemStack toBucketStack() {
        return switch (this) {
            case EMPTY -> ItemStack.EMPTY;
            case WATER -> new ItemStack(Items.WATER_BUCKET);
            case LAVA -> new ItemStack(Items.LAVA_BUCKET);
            case SEEP -> new ItemStack(ALMFluids.SEEP.bucket().get());
        };
    }
}