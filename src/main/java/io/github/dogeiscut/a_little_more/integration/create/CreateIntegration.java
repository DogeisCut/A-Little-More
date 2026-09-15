package io.github.dogeiscut.a_little_more.integration.create;

import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import io.github.dogeiscut.a_little_more.ALittleMore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreateIntegration {

    public static final DeferredRegister<FanProcessingType> FAN_TYPES =
            DeferredRegister.create(CreateRegistries.FAN_PROCESSING_TYPE, ALittleMore.MOD_ID);

    public static final Supplier<SeepFanProcessingType> SEEP_FAN_TYPE =
            FAN_TYPES.register("seeping", SeepFanProcessingType::new);

    public static void init(IEventBus modEventBus) {
        FAN_TYPES.register(modEventBus);
    }
}