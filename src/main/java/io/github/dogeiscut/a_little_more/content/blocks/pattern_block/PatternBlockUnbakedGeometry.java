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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PatternBlockUnbakedGeometry implements IUnbakedGeometry<PatternBlockUnbakedGeometry> {
    private static final List<String> PATTERN_NAMES = List.of(
            "border", "bricks", "circle", "creeper", "cross", "curly_border",
            "diagonal_left", "diagonal_right", "diagonal_up_left", "diagonal_up_right",
            "flow", "flower", "globe", "gradient", "gradient_up", "guster",
            "half_horizontal", "half_horizontal_bottom", "half_vertical", "half_vertical_right",
            "mojang", "mojang_real", "piglin", "rhombus", "skull", "small_stripes",
            "square_bottom_left", "square_bottom_right", "square_top_left", "square_top_right",
            "straight_cross",
            "stripe_bottom", "stripe_center", "stripe_downleft", "stripe_downright",
            "stripe_left", "stripe_middle", "stripe_right", "stripe_top",
            "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top"
    );

    @Override
    public @NotNull BakedModel bake(@NotNull IGeometryBakingContext context, @NotNull ModelBaker modelBaker, @NotNull Function<Material, TextureAtlasSprite> spriteGetter, @NotNull ModelState modelState, @NotNull ItemOverrides overrides) {
        TextureAtlasSprite baseSprite = spriteGetter.apply(new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ALittleMore.id("block/pattern_block/base")
        ));

        Map<String, TextureAtlasSprite> patternSprites = new HashMap<>();
        for (String name : PATTERN_NAMES) {
            patternSprites.put(name, spriteGetter.apply(new Material(
                    TextureAtlas.LOCATION_BLOCKS,
                    ALittleMore.id("block/pattern_block/" + name)
            )));
        }

        return new PatternBlockBakedModel(baseSprite, patternSprites);
    }
}
