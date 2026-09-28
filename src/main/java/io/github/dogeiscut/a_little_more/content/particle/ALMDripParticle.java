package io.github.dogeiscut.a_little_more.content.particle;

import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

public class ALMDripParticle extends DripParticle {
    protected ALMDripParticle(@NotNull ClientLevel level, double x, double y, double z, @NotNull Fluid type) {
        super(level, x, y, z, type);
    }


    public record DrippingSeepProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
            DripParticle dripparticle = new DripParticle.DripHangParticle(level, x, y, z, ALMFluids.SEEP.still().get(), ALMParticles.SEEP_SPLASH.get());
            dripparticle.setColor(0.878431373F, 0.643137255F, 0.97254902F);
            dripparticle.pickSprite(this.sprite());
            return dripparticle;
        }
    }

    public record FallingSeepProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
            DripParticle dripparticle = new DripParticle.FallAndLandParticle(level, x, y, z, ALMFluids.SEEP.still().get(), ALMParticles.SEEP_SPLASH.get());
            dripparticle.setColor(0.878431373F, 0.643137255F, 0.97254902F);
            dripparticle.pickSprite(this.sprite());
            return dripparticle;
        }
    }

    public record DrippingDripstoneSeepProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
            DripParticle dripparticle = new DripParticle.DripHangParticle(level, x, y, z, ALMFluids.SEEP.still().get(), ALMParticles.FALLING_DRIPSTONE_SEEP.get());
            dripparticle.setColor(0.878431373F, 0.643137255F, 0.97254902F);
            dripparticle.pickSprite(this.sprite());
            return dripparticle;
        }
    }

    public record FallingDripstoneSeepProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
            DripParticle dripparticle = new DripParticle.DripstoneFallAndLandParticle(level, x, y, z, ALMFluids.SEEP.still().get(), ALMParticles.SEEP_SPLASH.get());
            dripparticle.setColor(0.878431373F, 0.643137255F, 0.97254902F);
            dripparticle.pickSprite(this.sprite());
            return dripparticle;
        }
    }
}