package io.github.dogeiscut.a_little_more.content.imbuing;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// TODO: figure out how to make this data-driven
public record Imbuement(Item type, List<AttributeEntry> appliedAttributes, List<SpecialEffect> specialEffects) {

    public enum SpecialEffect implements StringRepresentable {
        DOUBLE_DURABILITY,
        INDESTRUCTIBLE_NONTOOL;

        @Override
        public @NotNull String getSerializedName() {
            return switch (this) {
                case DOUBLE_DURABILITY -> "double_durability";
                case INDESTRUCTIBLE_NONTOOL -> "indestructible_nontool";
            };
        }
    }

    public record AttributeEntry(Attribute attribute, double amount) {

    }
}
