package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.consumables.enseepened_pearl.EnseepenedPearlEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ALMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ALittleMore.MOD_ID);

    public static final Supplier<EntityType<EnseepenedPearlEntity>> ENSEEPENED_PEARL = ENTITY_TYPES.register("enseepened_pearl",
            () -> EntityType.Builder.<EnseepenedPearlEntity>of(EnseepenedPearlEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("enseepened_pearl"));

    public static <T extends Entity> Supplier<EntityType<T>> entity(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width,
            float height
    ) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category)
                .sized(width, height)
                .build(name));
    }

    public static <T extends Entity> Supplier<EntityType<T>> entity(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            java.util.function.UnaryOperator<EntityType.Builder<T>> customizer
    ) {
        return ENTITY_TYPES.register(name, () -> customizer
                .apply(EntityType.Builder.of(factory, category))
                .build(name));
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
