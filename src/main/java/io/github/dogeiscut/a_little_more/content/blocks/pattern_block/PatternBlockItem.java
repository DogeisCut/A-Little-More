package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PatternBlockItem extends BlockItem {
    public PatternBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static void appendHoverTextFromBannerBlockEntityTag(ItemStack stack, List<Component> tooltipComponents) {
        BannerPatternLayers patternLayers = stack.get(DataComponents.BANNER_PATTERNS);
        if (patternLayers != null) {
            for(int i = 0; i < Math.min(patternLayers.layers().size(), PatternBlockEntity.MAX_PATTERNS); ++i) {
                BannerPatternLayers.Layer layer = patternLayers.layers().get(i);
                tooltipComponents.add(layer.description().withStyle(ChatFormatting.GRAY));
            }
        }

    }

    public DyeColor getColor() {
        return ((AbstractBannerBlock)this.getBlock()).getColor();
    }

    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        appendHoverTextFromBannerBlockEntityTag(stack, tooltipComponents);
    }
}
