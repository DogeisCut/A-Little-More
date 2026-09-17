package io.github.dogeiscut.a_little_more.datagen;

import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;


public final class ALMBlockFamily {
    private final BlockFamily vanilla;
    private final boolean isStone;
    @Nullable
    private final RotatedPillarBlock pillar;

    private ALMBlockFamily(BlockFamily vanilla, boolean isStone, @Nullable RotatedPillarBlock pillar) {
        this.vanilla = vanilla;
        this.isStone = isStone;
        this.pillar = pillar;
    }

    public BlockFamily vanilla() {
        return vanilla;
    }

    public Block baseBlock() {
        return vanilla.getBaseBlock();
    }

    @Nullable
    public RotatedPillarBlock pillar() {
        return pillar;
    }

    public boolean hasPillar() {
        return pillar != null;
    }

    public boolean isStone() {
        return this.isStone;
    }

    public Stream<Block> allBlocks() {
        Stream<Block> base = Stream.of(baseBlock());
        Stream<Block> variants = vanilla.getVariants().values().stream();
        Stream<Block> pillarStream = hasPillar() ? Stream.of(pillar) : Stream.empty();
        return Stream.concat(Stream.concat(base, variants), pillarStream);
    }

    public static final class Builder {
        private final BlockFamily.Builder delegate;
        private boolean isStone;
        @Nullable
        private RotatedPillarBlock pillar;

        public Builder(Block baseBlock) {
            this.delegate = new BlockFamily.Builder(baseBlock);
        }

        public Builder slab(Block block) {
            delegate.slab(block);
            return this;
        }

        public Builder stairs(Block block) {
            delegate.stairs(block);
            return this;
        }

        public Builder wall(Block block) {
            delegate.wall(block);
            return this;
        }

        public Builder chiseled(Block block) {
            delegate.chiseled(block);
            return this;
        }

        public Builder polished(Block block) {
            delegate.polished(block);
            return this;
        }

        public Builder fence(Block block) {
            delegate.fence(block);
            return this;
        }

        public Builder pillar(RotatedPillarBlock block) {
            this.pillar = block;
            return this;
        }

        public ALMBlockFamily getFamily() {
            return new ALMBlockFamily(delegate.getFamily(), isStone, pillar);
        }

        public Builder stone() {
            this.isStone = true;
            return this;
        }
    }
}