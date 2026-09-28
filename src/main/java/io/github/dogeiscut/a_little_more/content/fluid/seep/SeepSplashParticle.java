package io.github.dogeiscut.a_little_more.content.fluid.seep;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

// Technically I could just reuse the already existing SplashParticle, but in case I want to do something special...
public class SeepSplashParticle extends WaterDropParticle {
    protected SeepSplashParticle(@NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z);
        this.gravity = 0.04F;
        if (ySpeed == (double) 0.0F && (xSpeed != (double) 0.0F || zSpeed != (double) 0.0F)) {
            this.xd = xSpeed;
            this.yd = 0.1;
            this.zd = zSpeed;
        }

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SeepSplashParticle seepSplashParticle = new SeepSplashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            seepSplashParticle.pickSprite(this.sprite);
            return seepSplashParticle;
        }
    }
}