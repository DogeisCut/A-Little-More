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

        add(ALMSounds.SEEP_TRANSFORM.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep/transform"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.block.seep.transform")
        );

        add(ALMSounds.OPOSSUM_AMBIENT.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle1"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle2"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle3"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).subtitle("subtitles.entity.opossum.ambient")
        );
        add(ALMSounds.OPOSSUM_HURT.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/hurt1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/hurt2"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.entity.opossum.hurt")
        );
        add(ALMSounds.OPOSSUM_DEATH.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/death1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/death2"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.entity.opossum.death")
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
