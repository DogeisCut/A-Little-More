package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import io.github.dogeiscut.a_little_more.registry.ALMPatternBlockPatterns;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class ALMPatternBlockPatternBootstrap {

    public static void bootstrap(BootstrapContext<PatternBlockPattern> context) {
        register(context, ALMPatternBlockPatterns.BORDER, "border");
        register(context, ALMPatternBlockPatterns.BRICKS, "bricks");
        register(context, ALMPatternBlockPatterns.CIRCLE, "circle");
        register(context, ALMPatternBlockPatterns.CREEPER, "creeper");
        register(context, ALMPatternBlockPatterns.CROSS, "cross");
        register(context, ALMPatternBlockPatterns.CURLY_BORDER, "curly_border");
        register(context, ALMPatternBlockPatterns.DIAGONAL_LEFT, "diagonal_left");
        register(context, ALMPatternBlockPatterns.DIAGONAL_RIGHT, "diagonal_right");
        register(context, ALMPatternBlockPatterns.DIAGONAL_UP_LEFT, "diagonal_up_left");
        register(context, ALMPatternBlockPatterns.DIAGONAL_UP_RIGHT, "diagonal_up_right");
        register(context, ALMPatternBlockPatterns.FLOW, "flow");
        register(context, ALMPatternBlockPatterns.FLOWER, "flower");
        register(context, ALMPatternBlockPatterns.GLOBE, "globe");
        register(context, ALMPatternBlockPatterns.GRADIENT, "gradient");
        register(context, ALMPatternBlockPatterns.GRADIENT_UP, "gradient_up");
        register(context, ALMPatternBlockPatterns.GUSTER, "guster");
        register(context, ALMPatternBlockPatterns.HALF_HORIZONTAL, "half_horizontal");
        register(context, ALMPatternBlockPatterns.HALF_HORIZONTAL_BOTTOM, "half_horizontal_bottom");
        register(context, ALMPatternBlockPatterns.HALF_VERTICAL, "half_vertical");
        register(context, ALMPatternBlockPatterns.HALF_VERTICAL_RIGHT, "half_vertical_right");
        register(context, ALMPatternBlockPatterns.MOJANG, "mojang");
        register(context, ALMPatternBlockPatterns.PIGLIN, "piglin");
        register(context, ALMPatternBlockPatterns.RHOMBUS, "rhombus");
        register(context, ALMPatternBlockPatterns.SKULL, "skull");
        register(context, ALMPatternBlockPatterns.SMALL_STRIPES, "small_stripes");
        register(context, ALMPatternBlockPatterns.SQUARE_BOTTOM_LEFT, "square_bottom_left");
        register(context, ALMPatternBlockPatterns.SQUARE_BOTTOM_RIGHT, "square_bottom_right");
        register(context, ALMPatternBlockPatterns.SQUARE_TOP_LEFT, "square_top_left");
        register(context, ALMPatternBlockPatterns.SQUARE_TOP_RIGHT, "square_top_right");
        register(context, ALMPatternBlockPatterns.STRAIGHT_CROSS, "straight_cross");
        register(context, ALMPatternBlockPatterns.STRIPE_BOTTOM, "stripe_bottom");
        register(context, ALMPatternBlockPatterns.STRIPE_CENTER, "stripe_center");
        register(context, ALMPatternBlockPatterns.STRIPE_DOWNLEFT, "stripe_downleft");
        register(context, ALMPatternBlockPatterns.STRIPE_DOWNRIGHT, "stripe_downright");
        register(context, ALMPatternBlockPatterns.STRIPE_LEFT, "stripe_left");
        register(context, ALMPatternBlockPatterns.STRIPE_MIDDLE, "stripe_middle");
        register(context, ALMPatternBlockPatterns.STRIPE_RIGHT, "stripe_right");
        register(context, ALMPatternBlockPatterns.STRIPE_TOP, "stripe_top");
        register(context, ALMPatternBlockPatterns.TRIANGLE_BOTTOM, "triangle_bottom");
        register(context, ALMPatternBlockPatterns.TRIANGLE_TOP, "triangle_top");
        register(context, ALMPatternBlockPatterns.TRIANGLES_BOTTOM, "triangles_bottom");
        register(context, ALMPatternBlockPatterns.TRIANGLES_TOP, "triangles_top");
    }

    private static void register(BootstrapContext<PatternBlockPattern> context, ResourceKey<PatternBlockPattern> key, String name) {
        ResourceLocation assetId = ALittleMore.id("block/pattern_block/" + name);
        String translationKey = "pattern.a_little_more." + name;
        context.register(key, new PatternBlockPattern(assetId, translationKey));
    }
}
