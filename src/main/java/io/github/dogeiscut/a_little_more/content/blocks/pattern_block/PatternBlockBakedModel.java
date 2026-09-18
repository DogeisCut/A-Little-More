package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMModelProperties;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PatternBlockBakedModel implements BakedModel {

    private final TextureAtlasSprite baseSprite;
    private final Map<String, TextureAtlasSprite> patternSprites;

    private final Map<CacheKey, List<BakedQuad>> quadCache = new ConcurrentHashMap<>();

    public PatternBlockBakedModel(TextureAtlasSprite baseSprite, Map<String, TextureAtlasSprite> patternSprites) {
        this.baseSprite = baseSprite;
        this.patternSprites = patternSprites;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, @NotNull RandomSource random) {
        return getQuads(state, direction, random, ModelData.EMPTY, null);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand,
                                             @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side == null) {
            return List.of();
        }

        DyeColor baseColor = extraData.get(ALMModelProperties.BASE_COLOR);
        BannerPatternLayers patterns = extraData.get(ALMModelProperties.BANNER_PATTERN_LAYERS);
        if (baseColor == null) baseColor = DyeColor.WHITE;
        if (patterns == null) patterns = BannerPatternLayers.EMPTY;

        Direction.Axis axis = state != null ? state.getValue(BlockStateProperties.AXIS) : Direction.Axis.Y;

        CacheKey key = new CacheKey(axis, side, patterns, renderType);
        BannerPatternLayers finalPatterns = patterns;
        return quadCache.computeIfAbsent(key, k -> buildQuads(axis, side, finalPatterns, renderType));
    }

    private List<BakedQuad> buildQuads(Direction.Axis axis, Direction side, BannerPatternLayers patterns, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();

        boolean wantBase = renderType == null || renderType.equals(RenderType.solid());
        boolean wantPatterns = renderType == null || renderType.equals(RenderType.translucent());

        if (wantBase) {
            quads.add(PatternBlockQuadBuilder.baseFaceQuad(side, baseSprite));
        }

        if (wantPatterns) {
            List<BannerPatternLayers.Layer> layers = patterns.layers();
            for (int i = 0; i < layers.size(); i++) {
                BannerPatternLayers.Layer layer = layers.get(i);
                TextureAtlasSprite sprite = spriteFor(layer.pattern());
                if (sprite == null) continue;
                quads.add(PatternBlockQuadBuilder.layerFaceQuad(side, sprite, i + 1, i + 1));
            }
        }
        return quads;
    }

    @Nullable
    private TextureAtlasSprite spriteFor(net.minecraft.core.Holder<BannerPattern> patternHolder) {
        ResourceLocation assetId = patternHolder.value().assetId();
        TextureAtlasSprite sprite = patternSprites.get(assetId.getPath());
        if (sprite == null) {
            ALittleMore.LOGGER.warn("Pattern block has no texture for banner pattern '{}' (looked for path '{}')",
                    assetId, assetId.getPath());
        }
        return sprite;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return baseSprite;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return baseSprite;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(RenderType.solid(), RenderType.translucent());
    }

    private record CacheKey(Direction.Axis axis, Direction side, BannerPatternLayers patterns, @Nullable RenderType renderType) {}
}
