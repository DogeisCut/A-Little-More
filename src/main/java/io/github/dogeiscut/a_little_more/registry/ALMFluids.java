package io.github.dogeiscut.a_little_more.registry;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepFlowingFluid;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepLiquidBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ALMFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ALittleMore.MOD_ID);

    public static final FluidEntry SEEP = fluid(
            "seep",
            ALMFluidTypes.SEEP,
            SeepFlowingFluid.Source::new,
            SeepFlowingFluid.Flowing::new,
            properties -> properties
                    .slopeFindDistance(2)
                    .levelDecreasePerBlock(2),
            liquidBlockProperties(MapColor.COLOR_PURPLE),
            defaultBucketItemProperties().rarity(Rarity.UNCOMMON),
            ((sourceSupplier, properties) ->
                    new SeepLiquidBlock(sourceSupplier, properties.lightLevel(state -> 5)))
    );

    public static final InternalFluidEntry SEEP_SODA = internalFluid(
            "seep_soda",
            ALMFluidTypes.SEEP_SODA,
            SeepFlowingFluid.Source::new,
            SeepFlowingFluid.Flowing::new,
            properties -> properties
                    .slopeFindDistance(2)
                    .levelDecreasePerBlock(2)
    );

    public static @NotNull FluidEntry fluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                            BlockBehaviour.@NotNull Properties blockProperties) {
        return fluid(name, fluidType, BaseFlowingFluid.Source::new, BaseFlowingFluid.Flowing::new, propertiesOp, blockProperties, defaultBucketItemProperties());
    }

    public static @NotNull FluidEntry fluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType) {
        return fluid(name, fluidType, UnaryOperator.identity(), liquidBlockProperties(MapColor.WATER));
    }

    public static @NotNull FluidEntry fluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                            BlockBehaviour.@NotNull Properties blockProperties, Item.@NotNull Properties bucketProperties) {
        return fluid(name, fluidType, BaseFlowingFluid.Source::new, BaseFlowingFluid.Flowing::new, propertiesOp, blockProperties, bucketProperties,
                (still, p) -> new LiquidBlock(still.get(), p));
    }

    public static @NotNull FluidEntry fluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> stillFactory,
                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> flowingFactory,
                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                            BlockBehaviour.@NotNull Properties blockProperties, Item.@NotNull Properties bucketProperties) {
        return fluid(name, fluidType, stillFactory, flowingFactory, propertiesOp, blockProperties, bucketProperties,
                (still, p) -> new LiquidBlock(still.get(), p));
    }

    public static @NotNull FluidEntry fluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> stillFactory,
                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> flowingFactory,
                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp,
                                            BlockBehaviour.@NotNull Properties blockProperties, Item.@NotNull Properties bucketProperties,
                                            @NotNull BiFunction<Supplier<? extends BaseFlowingFluid>, BlockBehaviour.Properties, LiquidBlock> blockFactory) {
        AtomicReference<BaseFlowingFluid.Properties> propertiesHolder = new AtomicReference<>();

        Supplier<? extends BaseFlowingFluid> still =
                FLUIDS.register(name, () -> stillFactory.apply(propertiesHolder.get()));
        Supplier<? extends BaseFlowingFluid> flowing =
                FLUIDS.register("flowing_" + name, () -> flowingFactory.apply(propertiesHolder.get()));

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

    public static @NotNull InternalFluidEntry internalFluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp) {
        return internalFluid(name, fluidType, BaseFlowingFluid.Source::new, BaseFlowingFluid.Flowing::new, propertiesOp);
    }

    public static @NotNull InternalFluidEntry internalFluid(@NotNull String name, @NotNull Supplier<? extends FluidType> fluidType,
                                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> stillFactory,
                                                            @NotNull Function<BaseFlowingFluid.Properties, ? extends BaseFlowingFluid> flowingFactory,
                                                            @NotNull UnaryOperator<BaseFlowingFluid.Properties> propertiesOp) {
        AtomicReference<BaseFlowingFluid.Properties> propertiesHolder = new AtomicReference<>();

        Supplier<? extends BaseFlowingFluid> still =
                FLUIDS.register(name, () -> stillFactory.apply(propertiesHolder.get()));
        Supplier<? extends BaseFlowingFluid> flowing =
                FLUIDS.register("flowing_" + name, () -> flowingFactory.apply(propertiesHolder.get()));

        propertiesHolder.set(propertiesOp.apply(
                new BaseFlowingFluid.Properties(fluidType, still, flowing)
        ));

        return new InternalFluidEntry(fluidType, still, flowing);
    }

    public static BlockBehaviour.@NotNull Properties liquidBlockProperties(@NotNull MapColor mapColor) {
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

    public static Item.@NotNull Properties defaultBucketItemProperties() {
        return new Item.Properties()
                .stacksTo(1)
                .craftRemainder(Items.BUCKET);
    }

    public static void register(@NotNull IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }

    public record FluidEntry(
            Supplier<? extends FluidType> type,
            Supplier<? extends BaseFlowingFluid> still,
            Supplier<? extends BaseFlowingFluid> flowing,
            DeferredBlock<LiquidBlock> block,
            Supplier<BucketItem> bucket
    ) {
    }

    public record InternalFluidEntry(
            Supplier<? extends FluidType> type,
            Supplier<? extends BaseFlowingFluid> still,
            Supplier<? extends BaseFlowingFluid> flowing
    ) {
    }
}
