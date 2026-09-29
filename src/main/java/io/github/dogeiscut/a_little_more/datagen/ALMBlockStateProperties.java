package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.content.fluid.Fluidlogged;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ALMBlockStateProperties {
    public static final EnumProperty<Fluidlogged> FLUIDLOGGED = EnumProperty.create("fluidlogged", Fluidlogged.class);

    public ALMBlockStateProperties() {
    }
}
