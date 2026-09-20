package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ALMBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ALittleMore.MOD_ID);

    @SafeVarargs
    public static <T extends BlockEntity> @NotNull DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> blockEntity(
            @NotNull String name,
            BlockEntityType.@NotNull BlockEntitySupplier<T> factory,
            Supplier<? extends Block> @NotNull ... blocks
    ) {
        return BLOCK_ENTITY_TYPES.register(name, () -> {
            Block[] resolved = new Block[blocks.length];
            for (int i = 0; i < blocks.length; i++) {
                resolved[i] = blocks[i].get();
            }
            return BlockEntityType.Builder.of(factory, resolved).build(null);
        });
    }    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PatternBlockEntity>> PATTERN_BLOCK_ENTITY =
            blockEntity("pattern_block", PatternBlockEntity::new, ALMBlocks.PATTERN_BLOCK);

    public static void register(@NotNull IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }


}
