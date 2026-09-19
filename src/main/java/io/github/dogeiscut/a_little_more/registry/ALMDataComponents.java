package io.github.dogeiscut.a_little_more.registry;

import com.mojang.serialization.Codec;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ALMDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ALittleMore.MOD_ID);

    public static final DataComponentType<PatternBlockFaces> PATTERN_BLOCK_FACES =
            register("pattern_block_faces", b -> b.persistent(PatternBlockFaces.CODEC));


    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
