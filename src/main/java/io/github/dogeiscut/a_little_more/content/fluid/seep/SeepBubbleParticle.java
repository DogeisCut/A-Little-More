package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class SeepBubbleParticle extends TextureSheetParticle {

    protected SeepBubbleParticle(@NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.4F;
        this.xd = xSpeed * (double) 0.2F + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
        this.yd = ySpeed * (double) 0.2F + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
        this.zd = zSpeed * (double) 0.2F + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
        this.lifetime = (int) ((double) 20.0F / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.yd += 0.002;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85F;
            this.yd *= 0.85F;
            this.zd *= 0.85F;
            if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(ALMTags.Fluids.SEEP)) {
                this.lifetime -= 3;
            }
        }

    }

    @Override
    public void remove() {
        if (!this.removed) {
            this.level.addParticle(
                    ALMParticles.SEEP_BUBBLE_POP.get(),
                    this.x, this.y, this.z,
                    0.0D, 0.0D, 0.0D
            );
        }
        super.remove();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SeepBubbleParticle particle = new SeepBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}