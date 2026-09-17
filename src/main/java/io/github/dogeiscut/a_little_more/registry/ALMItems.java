package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.armor.AttributeArmorItem;
import io.github.dogeiscut.a_little_more.content.consumables.enseepened_pearl.EnseepenedPearlItem;
import io.github.dogeiscut.a_little_more.content.weapons.flail.FlailItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class ALMItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ALittleMore.MOD_ID);

    public static final Supplier<Item> CELERIUM_SHARD = basicItem("celerium_shard");
    public static final Supplier<Item> EMERALD_NUGGET = basicItem("emerald_nugget");
    public static final Supplier<Item> OPOSSUM_TAIL = basicItem("opossum_tail");
    public static final Supplier<Item> SEEP_CRYSTAL = basicItem("seep_crystal");

    // TODO: Celerium tool/armor helper function(s)
    public static final Supplier<ArmorItem> CELERIUM_HELMET = item("celerium_helmet",
            properties -> new AttributeArmorItem(
                    ALMArmorMaterials.CELERIUM,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1),
                    ItemAttributeModifiers.builder()
                            .add(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_helmet_movement_speed"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.HEAD
                            )
                            .add(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_helmet_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.HEAD
                            )
                            .build()
            ));
    public static final Supplier<ArmorItem> CELERIUM_CHESTPLATE = item("celerium_chestplate",
            properties -> new AttributeArmorItem(
                    ALMArmorMaterials.CELERIUM,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1),
                    ItemAttributeModifiers.builder()
                            .add(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_chestplate_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.CHEST
                            )
                            .add(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_chestplate_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.CHEST
                            )
                            .build()
            ));
    public static final Supplier<ArmorItem> CELERIUM_LEGGINGS = item("celerium_leggings",
            properties -> new AttributeArmorItem(
                    ALMArmorMaterials.CELERIUM,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1),
                    ItemAttributeModifiers.builder()
                            .add(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_leggings_movement_speed"), 0.08, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.LEGS
                            )
                            .add(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_leggings_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.LEGS
                            )
                            .build()
            ));
    public static final Supplier<ArmorItem> CELERIUM_BOOTS = item("celerium_boots",
            properties -> new AttributeArmorItem(
                    ALMArmorMaterials.CELERIUM,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1),
                    ItemAttributeModifiers.builder()
                            .add(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_boots_movement_speed"), 0.08, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.FEET
                            )
                            .add(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_boots_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.FEET
                            )
                            .build()
            ));

    public static final Supplier<SwordItem> CELERIUM_SWORD = item("celerium_sword",
            properties -> new SwordItem(
                    ALMTiers.CELERIUM,
                    properties.attributes(SwordItem.createAttributes(ALMTiers.CELERIUM, 3, -1.0F)
                            .withModifierAdded(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_sword_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                            .withModifierAdded(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_sword_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
            ));

    public static final Supplier<PickaxeItem> CELERIUM_PICKAXE = item("celerium_pickaxe",
            properties -> new PickaxeItem(
                    ALMTiers.CELERIUM,
                    properties.attributes(PickaxeItem.createAttributes(ALMTiers.CELERIUM, 1.0F, -1.8F)
                            .withModifierAdded(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_pickaxe_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                            .withModifierAdded(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_pickaxe_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
            ));
    public static final Supplier<AxeItem> CELERIUM_AXE = item("celerium_axe",
            properties -> new AxeItem(
                    ALMTiers.CELERIUM,
                    properties.attributes(AxeItem.createAttributes(ALMTiers.CELERIUM, 4.5F, -2.0F)
                            .withModifierAdded(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_axe_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                            .withModifierAdded(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_axe_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
            ));
    public static final Supplier<ShovelItem> CELERIUM_SHOVEL = item("celerium_shovel",
            properties -> new ShovelItem(
                    ALMTiers.CELERIUM,
                    properties.attributes(ShovelItem.createAttributes(ALMTiers.CELERIUM, 1.5F, -1.7F)
                            .withModifierAdded(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_shovel_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                            .withModifierAdded(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_shovel_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
            ));
    public static final Supplier<HoeItem> CELERIUM_HOE = item("celerium_hoe",
            properties -> new HoeItem(
                    ALMTiers.CELERIUM,
                    properties.attributes(HoeItem.createAttributes(ALMTiers.CELERIUM, 0.0F, 0.0F)
                            .withModifierAdded(
                                    Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ALittleMore.id("celerium_hoe_movement_speed"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                    EquipmentSlotGroup.MAINHAND
                            ).withModifierAdded(
                                    ALMAttributes.TARGET_HURT_TIME,
                                    new AttributeModifier(ALittleMore.id("celerium_hoe_target_hurt_time"), -0.06, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
            ));

    public static final Supplier<EnseepenedPearlItem> ENSEEPENED_PEARL = item("enseepened_pearl", EnseepenedPearlItem::new);

    public static final Supplier<Item> IMBUEMENT_TEMPLATE = item("imbuement_template", Item::new);
    public static final Supplier<Item> ADVANCED_IMBUEMENT_TEMPLATE = item("advanced_imbuement_template", Item::new);

    public static final Supplier<Item> IMBUEMENT_BADGE = item("imbuement_badge", properties -> new Item(properties.stacksTo(1)));

    public static final Supplier<FlailItem> FLAIL = item("flail", properties -> new FlailItem(
            properties.rarity(Rarity.EPIC)
    ));

    public static final Supplier<Item> MUSIC_DISC_JUST_A_LITTLE_MORE = item("music_disc_just_a_little_more", (properties) -> new Item(properties
            .stacksTo(1)
            .rarity(Rarity.RARE)
            .jukeboxPlayable(
                    ResourceKey.create(
                            Registries.JUKEBOX_SONG,
                            ALittleMore.id("just_a_little_more")
                    )
            )
    ));

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

    public static @NotNull Supplier<Item> basicItem(@NotNull String name) {
        return ITEMS.registerSimpleItem(name);
    }

    public static @NotNull Supplier<Item> basicItem(@NotNull String name, Item.@NotNull Properties properties) {
        return ITEMS.registerSimpleItem(name, properties);
    }

    public static <T extends Item> @NotNull Supplier<T> item(@NotNull String name, @NotNull Function<Item.Properties, T> factory) {
        return ITEMS.registerItem(name, factory);
    }

    public static <T extends Item> @NotNull Supplier<T> item(@NotNull String name, @NotNull Function<Item.Properties, T> factory, Item.@NotNull Properties properties) {
        return ITEMS.registerItem(name, factory, properties);
    }

    public static <T extends Block> @NotNull Supplier<net.minecraft.world.item.BlockItem> blockItem(@NotNull DeferredBlock<T> block) {
        return ITEMS.registerSimpleBlockItem(block);
    }

    public static <T extends Block> @NotNull Supplier<net.minecraft.world.item.BlockItem> blockItem(@NotNull DeferredBlock<T> block, Item.@NotNull Properties properties) {
        return ITEMS.registerSimpleBlockItem(block, properties);
    }

    public static void register(@NotNull IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
