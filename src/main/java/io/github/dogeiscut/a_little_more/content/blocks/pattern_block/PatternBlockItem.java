package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PatternBlockItem extends BlockItem {

    public PatternBlockItem(@NotNull Block block, @NotNull Properties properties) {
        super(block, properties);
    }

    public static void appendHoverTextFromPatternBlockFaces(@NotNull ItemStack stack, @NotNull List<Component> tooltipComponents) {
        PatternBlockFaces faces = stack.get(ALMDataComponents.PATTERN_BLOCK_FACES);
        if (faces == null || faces.faces().isEmpty()) {
            return;
        }

        for (Direction direction : Direction.values()) {
            faces.getFace(direction).ifPresent(faceData -> {
                tooltipComponents.add(
                        Component.literal(direction.getName().toUpperCase() + ":")
                                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.BOLD)
                );

                tooltipComponents.add(
                        Component.translatable("color.minecraft." + faceData.baseColor().getName())
                                .withStyle(ChatFormatting.GRAY)
                );

                for (PatternBlockFaces.Layer layer : faceData.layers()) {
                    String translationKey = layer.pattern().value().translationKey() + "." + layer.color().getName();
                    tooltipComponents.add(
                            Component.translatable(translationKey)
                                    .withStyle(ChatFormatting.GRAY)
                    );
                }
            });
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                Item.@NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents,
                                @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        appendHoverTextFromPatternBlockFaces(stack, tooltipComponents);
    }
}