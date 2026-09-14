package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ALMItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ALittleMore.MOD_ID);

//    public static final Supplier<Item> item = item("celerium_shard", (p) -> new Item(
//            p.attributes(
//                    ItemAttributeModifiers.builder()
//                            .add(
//                                    Attributes.MOVEMENT_SPEED,
//                                    new AttributeModifier(
//                                            ALittleMore.id("")
//                                    )
//                            )
//            )
//    ));
    public static final Supplier<Item> CELERIUM_SHARD = basicItem("celerium_shard");



    public static final Supplier<Item> EMERALD_NUGGET = basicItem("emerald_nugget");
    public static final Supplier<Item> OPOSSUM_TAIL = basicItem("opossum_tail");

//    public static final Supplier<SpawnEggItem> OPOSSUM_SPAWN_EGG = ITEMS.registerItem(
//            "opossum_spawn_egg",
//            properties -> new SpawnEggItem(
//                    ALMEntities.OPOSSUM.get(),
//                    0xFFFFFF,
//                    0x000000,
//                    properties
//            ),
//            new Item.Properties()
//    );
//    public static final Supplier<SpawnEggItem> SHADY_DEALER_SPAWN_EGG = ITEMS.registerItem(
//            "shady_dealer_spawn_egg",
//            properties -> new SpawnEggItem(
//                    ALMEntities.SHADY_DEALER.get(),
//                    0xFFFFFF,
//                    0x000000,
//                    properties
//            ),
//            new Item.Properties()
//    );

    public static Supplier<Item> basicItem(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    public static Supplier<Item> basicItem(String name, Item.Properties properties) {
        return ITEMS.registerSimpleItem(name, properties);
    }

    public static <T extends Item> Supplier<T> item(String name, Function<Item.Properties, T> factory) {
        return ITEMS.registerItem(name, factory);
    }

    public static <T extends Item> Supplier<T> item(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
        return ITEMS.registerItem(name, factory, properties);
    }

    public static <T extends Block> Supplier<net.minecraft.world.item.BlockItem> blockItem(DeferredBlock<T> block) {
        return ITEMS.registerSimpleBlockItem(block);
    }

    public static <T extends Block> Supplier<net.minecraft.world.item.BlockItem> blockItem(DeferredBlock<T> block, Item.Properties properties) {
        return ITEMS.registerSimpleBlockItem(block, properties);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
