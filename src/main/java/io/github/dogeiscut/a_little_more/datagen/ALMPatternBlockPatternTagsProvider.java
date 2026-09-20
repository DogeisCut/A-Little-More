package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import io.github.dogeiscut.a_little_more.registry.ALMPatternBlockPatterns;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ALMPatternBlockPatternTagsProvider extends TagsProvider<PatternBlockPattern> {

    public ALMPatternBlockPatternTagsProvider(@NotNull PackOutput output,
                                              @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider,
                                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, PatternBlockPattern.REGISTRY_KEY, lookupProvider, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        add(ALMTags.PatternBlockPatterns.NO_ITEM_REQUIRED,
                ALMPatternBlockPatterns.SQUARE_BOTTOM_LEFT,
                ALMPatternBlockPatterns.SQUARE_BOTTOM_RIGHT,
                ALMPatternBlockPatterns.SQUARE_TOP_LEFT,
                ALMPatternBlockPatterns.SQUARE_TOP_RIGHT,
                ALMPatternBlockPatterns.STRIPE_BOTTOM,
                ALMPatternBlockPatterns.STRIPE_TOP,
                ALMPatternBlockPatterns.STRIPE_LEFT,
                ALMPatternBlockPatterns.STRIPE_RIGHT,
                ALMPatternBlockPatterns.STRIPE_CENTER,
                ALMPatternBlockPatterns.STRIPE_MIDDLE,
                ALMPatternBlockPatterns.STRIPE_DOWNRIGHT,
                ALMPatternBlockPatterns.STRIPE_DOWNLEFT,
                ALMPatternBlockPatterns.DIAGONAL_LEFT,
                ALMPatternBlockPatterns.DIAGONAL_RIGHT,
                ALMPatternBlockPatterns.DIAGONAL_UP_LEFT,
                ALMPatternBlockPatterns.DIAGONAL_UP_RIGHT,
                ALMPatternBlockPatterns.SMALL_STRIPES,
                ALMPatternBlockPatterns.CROSS,
                ALMPatternBlockPatterns.STRAIGHT_CROSS,
                ALMPatternBlockPatterns.TRIANGLE_BOTTOM,
                ALMPatternBlockPatterns.TRIANGLE_TOP,
                ALMPatternBlockPatterns.TRIANGLES_BOTTOM,
                ALMPatternBlockPatterns.TRIANGLES_TOP,
                ALMPatternBlockPatterns.CIRCLE,
                ALMPatternBlockPatterns.RHOMBUS,
                ALMPatternBlockPatterns.HALF_VERTICAL,
                ALMPatternBlockPatterns.HALF_VERTICAL_RIGHT,
                ALMPatternBlockPatterns.HALF_HORIZONTAL,
                ALMPatternBlockPatterns.HALF_HORIZONTAL_BOTTOM,
                ALMPatternBlockPatterns.BORDER,
                ALMPatternBlockPatterns.CURLY_BORDER,
                ALMPatternBlockPatterns.GRADIENT,
                ALMPatternBlockPatterns.GRADIENT_UP,
                ALMPatternBlockPatterns.BRICKS);

        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_FLOWER, ALMPatternBlockPatterns.FLOWER);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_CREEPER, ALMPatternBlockPatterns.CREEPER);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_SKULL, ALMPatternBlockPatterns.SKULL);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_MOJANG, ALMPatternBlockPatterns.MOJANG);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_GLOBE, ALMPatternBlockPatterns.GLOBE);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_PIGLIN, ALMPatternBlockPatterns.PIGLIN);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_FLOW, ALMPatternBlockPatterns.FLOW);
        add(ALMTags.PatternBlockPatterns.PATTERN_ITEM_GUSTER, ALMPatternBlockPatterns.GUSTER);

        // kinda want to add pattern block exclusive patterns at some point since they are different from banners
    }

    @SafeVarargs
    private void add(@NotNull TagKey<PatternBlockPattern> tag, @NotNull ResourceKey<PatternBlockPattern> @NotNull ... patterns) {
        TagAppender<PatternBlockPattern> appender = tag(tag);
        for (ResourceKey<PatternBlockPattern> pattern : patterns) {
            appender.add(pattern);
        }
    }
}
