package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class PatternBlockUnbakedGeometry implements IUnbakedGeometry<PatternBlockUnbakedGeometry> {
    @Override
    public @NotNull BakedModel bake(@NotNull IGeometryBakingContext context, @NotNull ModelBaker modelBaker, @NotNull Function<Material, TextureAtlasSprite> spriteGetter, @NotNull ModelState modelState, @NotNull ItemOverrides overrides) {
        TextureAtlasSprite baseSprite = spriteGetter.apply(new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ALittleMore.id("block/pattern_block/base")
        ));

        return new PatternBlockBakedModel(baseSprite);
    }
}
