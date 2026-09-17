package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ALMSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, ALittleMore.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> JUST_A_LITTLE_MORE = sound("music_disc.just_a_little_more");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUCKET_EMPTY_SEEP = sound("item.bucket.empty_seep");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUCKET_SEEP_FILL = sound("item.bucket.fill_seep");


    public static @NotNull DeferredHolder<SoundEvent, SoundEvent> sound(@NotNull String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ALittleMore.id(name)));
    }


    public static void register(@NotNull IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }
}
