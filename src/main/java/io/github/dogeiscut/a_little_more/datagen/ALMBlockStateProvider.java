package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
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
import java.util.Map;

public class ALMBlockStateProvider extends BlockStateProvider {

    private final List<String> missingTextures = new ArrayList<>();

    public ALMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ALittleMore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ALMFamilies.ALL_FAMILIES.forEach(this::blockFamily);

        ALMFamilies.SIMPLE_CUBES.forEach(this::simpleCubeAllWithItem);
        ALMFamilies.ORES.forEach(this::simpleCubeAllWithItem);
        ALMFamilies.AXE_MINEABLE.forEach(this::simpleCubeAllWithItem);

        // TODO: seep cluster assets, it's NOT going to be a cube.
        simpleCubeAllWithItem(ALMBlocks.SEEP_CRYSTAL_CLUSTER.get());

        particleOnly(ALMFluids.SEEP.block().get(), modLoc("block/seep_still"));

        if (!missingTextures.isEmpty()) {
            ALittleMore.LOGGER.warn(
                    "[A Little More datagen] Skipped {} block model(s) with no texture yet: {}",
                    missingTextures.size(), String.join(", ", missingTextures));
        }
    }

    public void simpleCubeAllWithItem(Block block) {
        if (skipIfNoTexture(block)) return;
        simpleBlockWithItem(block, cubeAll(block));
    }

    public void blockFamily(ALMBlockFamily family) {
        if (skipIfNoTexture(family.getBase())) return;

        ResourceLocation texture = blockTexture(family.getBase());
        ModelFile baseModel = cubeAll(family.getBase());
        simpleBlockWithItem(family.getBase(), baseModel);

        Map<ALMBlockFamily.Variant, Block> vars = family.getVariants();

        if (vars.containsKey(ALMBlockFamily.Variant.SLAB)) {
            SlabBlock slab = (SlabBlock) vars.get(ALMBlockFamily.Variant.SLAB);
            slabBlock(slab, modLoc("block/" + name(family.getBase())), texture);
            itemModelFromBlock(slab);
        }
        if (vars.containsKey(ALMBlockFamily.Variant.SLAB)) {
            StairBlock stairs = (StairBlock) vars.get(ALMBlockFamily.Variant.STAIRS);
            stairsBlock(stairs, texture);
            itemModelFromBlock(stairs);
        }
        if (vars.containsKey(ALMBlockFamily.Variant.WALL)) {
            WallBlock wall = (WallBlock) vars.get(ALMBlockFamily.Variant.WALL);
            wallBlock(wall, texture);
            itemModels().wallInventory(name(wall) + "_inventory", texture);
        }
        if (vars.containsKey(ALMBlockFamily.Variant.CHISELED)) {
            Block chiseled = vars.get(ALMBlockFamily.Variant.CHISELED);
            simpleCubeAllWithItem(chiseled);
        }
        if (vars.containsKey(ALMBlockFamily.Variant.PILLAR)) {
            RotatedPillarBlock pillar = (RotatedPillarBlock) vars.get(ALMBlockFamily.Variant.PILLAR);
            axisBlock(pillar, texture);
            itemModels().wallInventory(name(pillar) + "_inventory", texture);
        }

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
