package io.github.dogeiscut.a_little_more.content.fluid.seep;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class SeepTransformParticle extends TextureSheetParticle {
    private static final RandomSource RANDOM = RandomSource.create();
    private final @NotNull SpriteSet sprites;

    public SeepTransformParticle(@NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull SpriteSet sprites) {
        super(level, x, y, z, (double) 0.5F - RANDOM.nextDouble(), ySpeed, (double) 0.5F - RANDOM.nextDouble());
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.yd *= 0.2F;
        if (xSpeed == (double) 0.0F && zSpeed == (double) 0.0F) {
            this.xd *= 0.1F;
            this.zd *= 0.1F;
        }

        this.setColor(
                0.576470588f,
                0.376470588f,
                0.807843137f
        );

        this.quadSize *= 0.75F;
        this.lifetime = (int) ((double) 8.0F / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SeepTransformParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprite);
        }
    }
}