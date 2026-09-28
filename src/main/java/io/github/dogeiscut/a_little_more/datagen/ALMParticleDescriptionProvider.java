package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import org.jetbrains.annotations.NotNull;

public class ALMParticleDescriptionProvider extends ParticleDescriptionProvider {

    public ALMParticleDescriptionProvider(@NotNull PackOutput output, @NotNull ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(ALMParticles.SEEP_BUBBLE.get(), ALittleMore.id("seep_bubble"));
        spriteSet(ALMParticles.SEEP_BUBBLE_POP.get(), ALittleMore.id("seep_bubble_pop"), 5, false);
        spriteSet(ALMParticles.SEEP_TRANSFORM.get(), ResourceLocation.withDefaultNamespace("spell"), 8, true);
        sprite(ALMParticles.FALLING_SEEP.get(), ResourceLocation.withDefaultNamespace("drip_fall"));
        sprite(ALMParticles.DRIPPING_SEEP.get(), ResourceLocation.withDefaultNamespace("drip_hang"));
        sprite(ALMParticles.FALLING_DRIPSTONE_SEEP.get(), ResourceLocation.withDefaultNamespace("drip_fall"));
        sprite(ALMParticles.DRIPPING_DRIPSTONE_SEEP.get(), ResourceLocation.withDefaultNamespace("drip_hang"));
        spriteSet(ALMParticles.SEEP_SPLASH.get(), ALittleMore.id("seep_splash"), 4, false);
    }
}