package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.seep.SeepFluidType;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ALMBlockStateProvider extends BlockStateProvider {

    private final List<String> missingTextures = new ArrayList<>();

    public ALMBlockStateProvider(@NotNull PackOutput output, @NotNull ExistingFileHelper exFileHelper) {
        super(output, ALittleMore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ALMDatagen.BlockFamilies.getAllFamilies().forEach(this::family);

        ALMDatagen.BlockFamilies.SIMPLE_CUBES.forEach(this::simpleCubeAllWithItem);

        ModelFile patternModel = models().getBuilder("block/pattern_block")
                .parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .customLoader((builder, helper) -> new CustomLoaderBuilder<BlockModelBuilder>(
                        ALittleMore.id("pattern_block"),
                        builder,
                        helper,
                        false
                ) {
                })
                .end();

        simpleBlock(ALMBlocks.PATTERN_BLOCK.get(), patternModel);
        itemModels().getBuilder("pattern_block").parent(patternModel);

        Block stampingTable = ALMBlocks.STAMPING_TABLE.get();
        simpleBlockWithItem(stampingTable, models().cubeBottomTop(
                name(stampingTable),
                modLoc("block/stamping_table_side"),
                modLoc("block/stamping_table_bottom"),
                modLoc("block/stamping_table_top")
        ));

        // TODO: seep cluster assets, it's NOT going to be a cube.
        simpleCubeAllWithItem(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get());

        particleOnly(ALMFluids.SEEP.block().get(), modLoc("block/seep_still"));

        singleLevelCauldronBlock(ALMBlocks.SEEP_CAULDRON.get(), SeepFluidType.STILL, false /* i know seep is transparent but vanilla water cauldrons aren't and it causes weird block outline artifacts*/);

        if (!missingTextures.isEmpty()) {
            ALittleMore.LOGGER.warn(
                    "[A Little More datagen] Skipped {} block model(s) with no texture yet: {}",
                    missingTextures.size(), String.join(", ", missingTextures));
        }
    }

    // WHY IS THERE NO BUILT-IN DATAGEN FUNCTION FOR CAULDRONS, MOJANG!!!!!!!!!!!!!!!!!!?????????????????
    public void singleLevelCauldronBlock(@NotNull Block block, @NotNull ResourceLocation fluidTexture, boolean translucent) {
        ResourceLocation cauldronTop = mcLoc("block/cauldron_top");
        ResourceLocation cauldronSide = mcLoc("block/cauldron_side");
        ResourceLocation cauldronBottom = mcLoc("block/cauldron_bottom");
        ResourceLocation cauldronInner = mcLoc("block/cauldron_inner");

        String blockName = name(block);

        BlockModelBuilder model = models().withExistingParent(blockName, mcLoc("block/template_cauldron_full"))
                .texture("top", cauldronTop)
                .texture("side", cauldronSide)
                .texture("bottom", cauldronBottom)
                .texture("inside", cauldronInner)
                .texture("content", fluidTexture);

        if (translucent) {
            model.renderType(mcLoc("translucent"));
        }

        simpleBlock(block, model);
    }


    private void family(@NotNull ALMBlockFamily almFamily) {
        BlockFamily family = almFamily.vanilla();
        if (!family.shouldGenerateModel()) return;
        Block base = family.getBaseBlock();

        if (skipIfNoTexture(base)) return;
        ResourceLocation baseTexture = blockTexture(base);
        simpleBlockWithItem(base, cubeAll(base));

        family.getVariants().forEach((variant, block) -> {
            if (variant == BlockFamily.Variant.SLAB) {
                ResourceLocation doubleSlabModel = modLoc("block/" + name(base));
                slabBlock((SlabBlock) block, doubleSlabModel, baseTexture);
                itemModelFromBlock(block);
            } else if (variant == BlockFamily.Variant.STAIRS) {
                stairsBlock((StairBlock) block, baseTexture);
                itemModelFromBlock(block);
            } else if (variant == BlockFamily.Variant.WALL) {
                wallBlock((WallBlock) block, baseTexture);
                itemModels().wallInventory(name(block), baseTexture);
            } else if (variant == BlockFamily.Variant.CHISELED) {
                simpleCubeAllWithItem(block);
            } else {
                ALittleMore.LOGGER.warn(
                        "[A Little More datagen] No model generator wired up for block family variant {} on {}",
                        variant, name(block));
            }
        });

        if (almFamily.hasPillar()) {
            // this is never null but INTELLIJ won't stop complaining
            assert almFamily.pillar() != null;
            pillarWithItem(almFamily.pillar());
        }
    }

    private void pillarWithItem(@NotNull RotatedPillarBlock pillarBlock) {
        ResourceLocation side = modLoc("block/" + name(pillarBlock) + "_side");
        ResourceLocation end = modLoc("block/" + name(pillarBlock) + "_top");

        boolean hasSide = models().existingFileHelper.exists(side, ModelProvider.TEXTURE);
        boolean hasEnd = models().existingFileHelper.exists(end, ModelProvider.TEXTURE);

        if (!hasSide) {
            missingTextures.add(side.toString());
        }
        if (!hasEnd) {
            missingTextures.add(end.toString());
        }

        if (!hasSide || !hasEnd) return;

        axisBlock(pillarBlock, side, end);
        itemModelFromBlock(pillarBlock);
    }

    public void simpleCubeAllWithItem(@NotNull Block block) {
        if (skipIfNoTexture(block)) return;
        simpleBlockWithItem(block, cubeAll(block));
    }

    public void particleOnly(@NotNull Block block, @NotNull ResourceLocation particle) {
        simpleBlock(block, models()
                .getBuilder(name(block))
                .texture("particle", particle));
    }

    private void itemModelFromBlock(@NotNull Block block) {
        String path = name(block);
        itemModels().withExistingParent(path, modLoc("block/" + path));
    }

    private boolean skipIfNoTexture(@NotNull Block block) {
        ResourceLocation texture = blockTexture(block);
        if (models().existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
            return false;
        }
        missingTextures.add(texture.toString());
        return true;
    }

    private @NotNull String name(@NotNull Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    @SuppressWarnings("unused")
    private ConfiguredModel @NotNull [] single(@NotNull ModelFile file) {
        return ConfiguredModel.builder().modelFile(file).build();
    }
}
