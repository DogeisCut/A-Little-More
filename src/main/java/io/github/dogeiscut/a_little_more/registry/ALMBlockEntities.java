package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ALMBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ALittleMore.MOD_ID);

    @SafeVarargs
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> blockEntity(
            String name,
            BlockEntityType.BlockEntitySupplier<T> factory,
            Supplier<? extends Block>... blocks
    ) {
        return BLOCK_ENTITY_TYPES.register(name, () -> {
            Block[] resolved = new Block[blocks.length];
            for (int i = 0; i < blocks.length; i++) {
                resolved[i] = blocks[i].get();
            }
            return BlockEntityType.Builder.of(factory, resolved).build(null);
        });
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
