package io.github.dogeiscut.a_little_more.registry;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.neoforge.client.model.data.ModelProperty;

public class ALMModelProperties {
    public static final ModelProperty<DyeColor> BASE_COLOR = new ModelProperty<>();
    public static final ModelProperty<BannerPatternLayers> BANNER_PATTERN_LAYERS = new ModelProperty<>();
}
