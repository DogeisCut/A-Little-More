package io.github.dogeiscut.a_little_more.datagen;

import net.minecraft.world.level.block.Block;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ALMBlockFamily {
    private final Block base;
    private final Map<Variant, Block> variants;
    private final List<ALMBlockFamily> derivatives;

    public enum Variant { SLAB, STAIRS, WALL, CHISELED, PILLAR }

    private ALMBlockFamily(Builder builder) {
        this.base = builder.base;
        this.variants = Map.copyOf(builder.variants);
        this.derivatives = List.copyOf(builder.derivatives);
    }

    public Block getBase() { return base; }
    public Map<Variant, Block> getVariants() { return variants; }
    public List<ALMBlockFamily> getDerivatives() { return derivatives; }

    public List<Block> getAllBlocks() {
        List<Block> all = new ArrayList<>();
        all.add(base);
        all.addAll(variants.values());
        return all;
    }

    public static class Builder {
        private final Block base;
        private final Map<Variant, Block> variants = new EnumMap<>(Variant.class);
        private final List<ALMBlockFamily> derivatives = new ArrayList<>();

        public Builder(Block base) { this.base = base; }

        public Builder slab(Block block) { variants.put(Variant.SLAB, block); return this; }
        public Builder stairs(Block block) { variants.put(Variant.STAIRS, block); return this; }
        public Builder wall(Block block) { variants.put(Variant.WALL, block); return this; }
        public Builder chiseled(Block block) { variants.put(Variant.CHISELED, block); return this; }
        public Builder pillar(Block block) { variants.put(Variant.PILLAR, block); return this; }

        public Builder derivative(ALMBlockFamily family) {
            derivatives.add(family);
            return this;
        }

        public ALMBlockFamily build() { return new ALMBlockFamily(this); }
    }
}