package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMParticles;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ALMParticleDescriptionProvider extends ParticleDescriptionProvider {

    public ALMParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(ALMParticles.SEEP_BUBBLE.get(), ALittleMore.id("seep_bubble"));
        spriteSet(ALMParticles.SEEP_BUBBLE_POP.get(), ALittleMore.id("seep_bubble_pop"), 5, false);
    }
}