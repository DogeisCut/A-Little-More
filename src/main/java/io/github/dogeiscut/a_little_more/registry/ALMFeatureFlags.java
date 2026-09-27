package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public class ALMFeatureFlags {
    public static final FeatureFlag PATTERN_STAMPING =
            FeatureFlags.REGISTRY.getFlag(ALittleMore.id("pattern_stamping"));

    public static final FeatureFlagSet PATTERN_STAMPING_SET = FeatureFlagSet.of(PATTERN_STAMPING);
}
