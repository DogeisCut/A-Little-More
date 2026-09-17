package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ALMSoundProvider extends SoundDefinitionsProvider {

    public ALMSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ALittleMore.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        music(ALMSounds.JUST_A_LITTLE_MORE.get(), "records/just_a_little_more");
    }

    public void music(SoundEvent event, String file) {
        add(event, SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                                ALittleMore.id(file), SoundDefinition.SoundType.SOUND)
                        .stream()));
    }

    public void sound(SoundEvent event, String file, String subtitle) {
        add(event, SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id(file), SoundDefinition.SoundType.SOUND))
                .subtitle(subtitle));
    }
}
