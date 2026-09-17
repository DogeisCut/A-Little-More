package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.NotNull;

public class ALMSoundProvider extends SoundDefinitionsProvider {

    public ALMSoundProvider(@NotNull PackOutput output, @NotNull ExistingFileHelper helper) {
        super(output, ALittleMore.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        music(ALMSounds.JUST_A_LITTLE_MORE.get(), "records/just_a_little_more");
        add(ALMSounds.BUCKET_EMPTY_SEEP.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep2"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep3"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.item.bucket.empty")
        );
        add(ALMSounds.BUCKET_SEEP_FILL.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep2"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep3"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.item.bucket.fill")
        );
    }

    public void music(@NotNull SoundEvent event, @NotNull String file) {
        add(event, SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                                ALittleMore.id(file), SoundDefinition.SoundType.SOUND)
                        .stream()));
    }

    public void sound(@NotNull SoundEvent event, @NotNull String file, String subtitle) {
        add(event, SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id(file), SoundDefinition.SoundType.SOUND))
                .subtitle(subtitle));
    }
}
