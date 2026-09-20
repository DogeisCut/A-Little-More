package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

public class ALMPatternBlockPatterns {
    public static final ResourceKey<PatternBlockPattern> BORDER = create("border");
    public static final ResourceKey<PatternBlockPattern> BRICKS = create("bricks");
    public static final ResourceKey<PatternBlockPattern> CIRCLE = create("circle");
    public static final ResourceKey<PatternBlockPattern> CREEPER = create("creeper");
    public static final ResourceKey<PatternBlockPattern> CROSS = create("cross");
    public static final ResourceKey<PatternBlockPattern> CURLY_BORDER = create("curly_border");
    public static final ResourceKey<PatternBlockPattern> DIAGONAL_LEFT = create("diagonal_left");
    public static final ResourceKey<PatternBlockPattern> DIAGONAL_RIGHT = create("diagonal_right");
    public static final ResourceKey<PatternBlockPattern> DIAGONAL_UP_LEFT = create("diagonal_up_left");
    public static final ResourceKey<PatternBlockPattern> DIAGONAL_UP_RIGHT = create("diagonal_up_right");
    public static final ResourceKey<PatternBlockPattern> FLOW = create("flow");
    public static final ResourceKey<PatternBlockPattern> FLOWER = create("flower");
    public static final ResourceKey<PatternBlockPattern> GLOBE = create("globe");
    public static final ResourceKey<PatternBlockPattern> GRADIENT = create("gradient");
    public static final ResourceKey<PatternBlockPattern> GRADIENT_UP = create("gradient_up");
    public static final ResourceKey<PatternBlockPattern> GUSTER = create("guster");
    public static final ResourceKey<PatternBlockPattern> HALF_HORIZONTAL = create("half_horizontal");
    public static final ResourceKey<PatternBlockPattern> HALF_HORIZONTAL_BOTTOM = create("half_horizontal_bottom");
    public static final ResourceKey<PatternBlockPattern> HALF_VERTICAL = create("half_vertical");
    public static final ResourceKey<PatternBlockPattern> HALF_VERTICAL_RIGHT = create("half_vertical_right");
    public static final ResourceKey<PatternBlockPattern> MOJANG = create("mojang");
    public static final ResourceKey<PatternBlockPattern> PIGLIN = create("piglin");
    public static final ResourceKey<PatternBlockPattern> RHOMBUS = create("rhombus");
    public static final ResourceKey<PatternBlockPattern> SKULL = create("skull");
    public static final ResourceKey<PatternBlockPattern> SMALL_STRIPES = create("small_stripes");
    public static final ResourceKey<PatternBlockPattern> SQUARE_BOTTOM_LEFT = create("square_bottom_left");
    public static final ResourceKey<PatternBlockPattern> SQUARE_BOTTOM_RIGHT = create("square_bottom_right");
    public static final ResourceKey<PatternBlockPattern> SQUARE_TOP_LEFT = create("square_top_left");
    public static final ResourceKey<PatternBlockPattern> SQUARE_TOP_RIGHT = create("square_top_right");
    public static final ResourceKey<PatternBlockPattern> STRAIGHT_CROSS = create("straight_cross");
    public static final ResourceKey<PatternBlockPattern> STRIPE_BOTTOM = create("stripe_bottom");
    public static final ResourceKey<PatternBlockPattern> STRIPE_CENTER = create("stripe_center");
    public static final ResourceKey<PatternBlockPattern> STRIPE_DOWNLEFT = create("stripe_downleft");
    public static final ResourceKey<PatternBlockPattern> STRIPE_DOWNRIGHT = create("stripe_downright");
    public static final ResourceKey<PatternBlockPattern> STRIPE_LEFT = create("stripe_left");
    public static final ResourceKey<PatternBlockPattern> STRIPE_MIDDLE = create("stripe_middle");
    public static final ResourceKey<PatternBlockPattern> STRIPE_RIGHT = create("stripe_right");
    public static final ResourceKey<PatternBlockPattern> STRIPE_TOP = create("stripe_top");
    public static final ResourceKey<PatternBlockPattern> TRIANGLE_BOTTOM = create("triangle_bottom");
    public static final ResourceKey<PatternBlockPattern> TRIANGLE_TOP = create("triangle_top");
    public static final ResourceKey<PatternBlockPattern> TRIANGLES_BOTTOM = create("triangles_bottom");
    public static final ResourceKey<PatternBlockPattern> TRIANGLES_TOP = create("triangles_top");

    private static @NotNull ResourceKey<PatternBlockPattern> create(@NotNull String name) {
        return ResourceKey.create(
                PatternBlockPattern.REGISTRY_KEY,
                ALittleMore.id(name)
        );
    }
}
