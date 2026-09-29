package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMSoundEvents;
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
        music(ALMSoundEvents.JUST_A_LITTLE_MORE.get(), "records/just_a_little_more");
        add(ALMSoundEvents.BUCKET_EMPTY_SEEP.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep2"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/empty_seep3"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.item.bucket.empty")
        );
        add(ALMSoundEvents.BUCKET_SEEP_FILL.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep2"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("item/bucket/fill_seep3"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.item.bucket.fill")
        );

        add(ALMSoundEvents.SEEP_TRANSFORM.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep/transform"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.block.seep.transform")
        );

        add(ALMSoundEvents.OPOSSUM_AMBIENT.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle1"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle2"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/idle3"), SoundDefinition.SoundType.SOUND
                ).volume(0.4)).subtitle("subtitles.entity.opossum.ambient")
        );
        add(ALMSoundEvents.OPOSSUM_HURT.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/hurt1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/hurt2"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.entity.opossum.hurt")
        );
        add(ALMSoundEvents.OPOSSUM_DEATH.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/death1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("mob/opossum/death2"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.entity.opossum.death")
        );

        add(ALMSoundEvents.SEEP_CRYSTAL_CLUSTER_ADD_ITEM.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep_crystal_cluster/add_item1"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep_crystal_cluster/add_item2"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep_crystal_cluster/add_item3"), SoundDefinition.SoundType.SOUND
                )).with(SoundDefinition.Sound.sound(
                        ALittleMore.id("block/seep_crystal_cluster/add_item4"), SoundDefinition.SoundType.SOUND
                )).subtitle("subtitles.block.seep_crystal_cluster.add_item")
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
