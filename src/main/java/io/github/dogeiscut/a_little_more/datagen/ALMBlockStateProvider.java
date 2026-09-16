package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

public class ALMBlockStateProvider extends BlockStateProvider {

    private final List<String> missingTextures = new ArrayList<>();

    public ALMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ALittleMore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ALMFamilies.STONE_SETS.forEach(this::stoneSet);

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

    public void stoneSet(ALMFamilies.StoneSet set) {
        if (skipIfNoTexture(set.base())) return;

        ResourceLocation texture = blockTexture(set.base());
        ModelFile baseModel = cubeAll(set.base());
        simpleBlockWithItem(set.base(), baseModel);

        if (set.slab() != null) {
            SlabBlock slab = set.slab();
            slabBlock(slab, modLoc("block/" + name(set.base())), texture);
            itemModelFromBlock(set.slab());
        }
        if (set.stairs() != null) {
            StairBlock stairs = set.stairs();
            stairsBlock(stairs, texture);
            itemModelFromBlock(set.stairs());
        }
        if (set.wall() != null) {
            WallBlock wall = set.wall();
            wallBlock(wall, texture);
            itemModels().wallInventory(name(set.wall()) + "_inventory", texture);
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
