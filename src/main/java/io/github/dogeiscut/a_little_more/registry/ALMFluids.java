package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepLiquidBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ALMFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ALittleMore.MOD_ID);

    public static final FluidEntry SEEP = fluid(
            "seep",
            ALMFluidTypes.SEEP,
            properties -> properties
                    .slopeFindDistance(2)
                    .levelDecreasePerBlock(2),
            liquidBlockProperties(MapColor.COLOR_PURPLE),
            defaultBucketItemProperties(),
            SeepLiquidBlock::new
    );

    public static FluidEntry fluid(String name, Supplier<? extends FluidType> fluidType,
                                   UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                   BlockBehaviour.Properties blockProperties) {
        return fluid(name, fluidType, propertiesOp, blockProperties, defaultBucketItemProperties());
    }

    public static FluidEntry fluid(String name, Supplier<? extends FluidType> fluidType) {
        return fluid(name, fluidType, UnaryOperator.identity(), liquidBlockProperties(MapColor.WATER));
    }

    public static FluidEntry fluid(String name, Supplier<? extends FluidType> fluidType,
                                   UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                   BlockBehaviour.Properties blockProperties, Item.Properties bucketProperties) {
        return fluid(name, fluidType, propertiesOp, blockProperties, bucketProperties,
                (still, p) -> new LiquidBlock(still.get(), p));
    }

    public static FluidEntry fluid(String name, Supplier<? extends FluidType> fluidType,
                                   UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                   BlockBehaviour.Properties blockProperties, Item.Properties bucketProperties,
                                   BiFunction<Supplier<BaseFlowingFluid.Source>, BlockBehaviour.Properties, LiquidBlock> blockFactory) {
        AtomicReference<BaseFlowingFluid.Properties> propertiesHolder = new AtomicReference<>();

        Supplier<BaseFlowingFluid.Source> still =
                FLUIDS.register(name, () -> new BaseFlowingFluid.Source(propertiesHolder.get()));
        Supplier<BaseFlowingFluid.Flowing> flowing =
                FLUIDS.register("flowing_" + name, () -> new BaseFlowingFluid.Flowing(propertiesHolder.get()));

        DeferredBlock<LiquidBlock> block = ALMBlocks.block(
                name, p -> blockFactory.apply(still, p), blockProperties);

        Supplier<BucketItem> bucket = ALMItems.item(
                name + "_bucket", p -> new BucketItem(still.get(), p), bucketProperties);

        propertiesHolder.set(propertiesOp.apply(
                new BaseFlowingFluid.Properties(fluidType, still, flowing)
                        .block(block)
                        .bucket(bucket)
        ));

        return new FluidEntry(fluidType, still, flowing, block, bucket);
    }

    public static BlockBehaviour.Properties liquidBlockProperties(MapColor mapColor) {
        return BlockBehaviour.Properties.of()
                .mapColor(mapColor)
                .replaceable()
                .noCollission()
                .strength(100.0F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .liquid()
                .sound(SoundType.EMPTY);
    }

    public static Item.Properties defaultBucketItemProperties() {
        return new Item.Properties()
                .stacksTo(1)
                .craftRemainder(Items.BUCKET);
    }

    public static void register(IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }

    public record FluidEntry(
            Supplier<? extends FluidType> type,
            Supplier<BaseFlowingFluid.Source> still,
            Supplier<BaseFlowingFluid.Flowing> flowing,
            DeferredBlock<LiquidBlock> block,
            Supplier<BucketItem> bucket
    ) {
    }
}