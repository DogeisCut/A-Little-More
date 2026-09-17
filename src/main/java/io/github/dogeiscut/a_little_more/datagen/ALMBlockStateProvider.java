package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;

public class ALMBlockStateProvider extends BlockStateProvider {

    private final List<String> missingTextures = new ArrayList<>();

    public ALMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ALittleMore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ALMBlockFamilies.getAllFamilies().forEach(this::family);

        ALMBlockFamilies.SIMPLE_CUBES.forEach(this::simpleCubeAllWithItem);

        // TODO: seep cluster assets, it's NOT going to be a cube.
        simpleCubeAllWithItem(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get());

        particleOnly(ALMFluids.SEEP.block().get(), modLoc("block/seep_still"));

        if (!missingTextures.isEmpty()) {
            ALittleMore.LOGGER.warn(
                    "[A Little More datagen] Skipped {} block model(s) with no texture yet: {}",
                    missingTextures.size(), String.join(", ", missingTextures));
        }
    }

    private void family(ALMBlockFamily almFamily) {
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
            pillarWithItem(almFamily.pillar());
        }
    }

    private void pillarWithItem(RotatedPillarBlock pillarBlock) {
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

    public void simpleCubeAllWithItem(Block block) {
        if (skipIfNoTexture(block)) return;
        simpleBlockWithItem(block, cubeAll(block));
    }

    public void particleOnly(Block block, ResourceLocation particle) {
        simpleBlock(block, models()
                .getBuilder(name(block))
                .texture("particle", particle));
    }

    private void itemModelFromBlock(Block block) {
        String path = name(block);
        itemModels().withExistingParent(path, modLoc("block/" + path));
    }

    private boolean skipIfNoTexture(Block block) {
        ResourceLocation texture = blockTexture(block);
        if (models().existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
            return false;
        }
        missingTextures.add(texture.toString());
        return true;
    }

    private String name(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    @SuppressWarnings("unused")
    private ConfiguredModel[] single(ModelFile file) {
        return ConfiguredModel.builder().modelFile(file).build();
    }
}
