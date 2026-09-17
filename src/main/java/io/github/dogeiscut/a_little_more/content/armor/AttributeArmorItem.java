package io.github.dogeiscut.a_little_more.content.armor;

import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AttributeArmorItem extends ArmorItem {
    private final @NotNull Supplier<ItemAttributeModifiers> combinedModifiers;

    public AttributeArmorItem(@NotNull Holder<ArmorMaterial> material, @NotNull Type type, @NotNull Properties properties,
                              @NotNull ItemAttributeModifiers extraModifiers) {
        super(material, type, properties);

        this.combinedModifiers = Suppliers.memoize(() -> {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            for (ItemAttributeModifiers.Entry entry : super.getDefaultAttributeModifiers().modifiers()) {
                builder.add(entry.attribute(), entry.modifier(), entry.slot());
            }

            for (ItemAttributeModifiers.Entry entry : extraModifiers.modifiers()) {
                builder.add(entry.attribute(), entry.modifier(), entry.slot());
            }

            return builder.build();
        });
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.combinedModifiers.get();
    }
}