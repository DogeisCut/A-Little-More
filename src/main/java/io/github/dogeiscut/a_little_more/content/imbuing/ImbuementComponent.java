package io.github.dogeiscut.a_little_more.content.imbuing;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;


public record ImbuementComponent(Supplier<Imbuement> imbuement) {
}
